package com.avioconsulting.mule.email.util.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import com.avioconsulting.mule.email.util.api.processor.AttachmentAttribute;
import com.avioconsulting.mule.email.util.api.processor.AttachmentAttributes;
import com.avioconsulting.mule.email.util.api.processor.EmailProperties;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.codec.binary.Base64;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class MuleEmailUtilModuleOperations {

  private static String HTML_MSG_CONTENT_TYPE = "text/html";
  private static String TEXT_MSG_CONTENT_TYPE = "text/plain";

  /**
   * generateEmailContent method creates Base64 encoded Email message in raw
   * format, that can be used in Google email API.
   * returns String
   */
  @DisplayName("Generate Email Content")
  @MediaType(value = ANY, strict = false)
  public String generateEmailContent(@ParameterGroup(name = "Email Info") EmailProperties emailProperties,
      @ParameterGroup(name = "Attachment Info") AttachmentAttributes attachmentAttributes) {
    String encodedEmail = "";
    try {
      byte[] emailContent = createEmailContent(emailProperties, attachmentAttributes);
      encodedEmail = encodeString(emailContent);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // String encodedEmail = encodeString(emailContent);
    return encodedEmail;
  }

  /**
   * createEmailContent method generates email message using the EmailProperties
   * (From, To, cc, bcc, MessageContent, subject)
   * and AttachmentAttributes (attachment content, filename, content-type)
   * returns byte[]
   */
  private byte[] createEmailContent(EmailProperties emailProperties, AttachmentAttributes attachmentAttributes)
      throws MessagingException, IOException {
    String fromAddress = emailProperties.getFrom();
    String toAddress = emailProperties.getTo();
    String ccAddress = emailProperties.getCc();
    String bccAddress = emailProperties.getBcc();
    String messageSubject = emailProperties.getSubject();
    String messageContentType = emailProperties.getContentType();
    String messageContent = emailProperties.getContent();

    List<AttachmentAttribute> attachmentList = attachmentAttributes.getAttributeList();

    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    MimeMessage emailMessage = new MimeMessage(session);
    emailMessage.setFrom(new InternetAddress(fromAddress));

    String[] toAddressList = toAddress.split("[,]", 0);
    for (String toEmailAddress : toAddressList) {
      emailMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmailAddress));
    }
    if (ccAddress != null && ccAddress != "") {
      String[] ccAddressList = ccAddress.split("[,]", 0);
      for (String ccEmailAddress : ccAddressList) {
        emailMessage.addRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress(ccEmailAddress));
      }
    }
    if (bccAddress != null && bccAddress != "") {
      String[] bccAddressList = bccAddress.split("[,]", 0);
      for (String bccEmailAddress : bccAddressList) {
        emailMessage.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress(bccEmailAddress));
      }
    }
    emailMessage.setSubject(messageSubject);

    Multipart multipart = new MimeMultipart();
    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    /**
     * If attachmentAttributes is passed in the request,
     * decode attachmentContent and include to email content.
     */
    if (attachmentList != null && attachmentList.size() > 0) {
      for (AttachmentAttribute attachment : attachmentList) {
        mimeBodyPart = new MimeBodyPart();
        byte[] attachmentContent = decodeString(attachment.getContent());
        DataSource source = new ByteArrayDataSource(attachmentContent, attachment.getContentType());
        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(attachment.getFilename());
        multipart.addBodyPart(mimeBodyPart);
        emailMessage.setContent(multipart);
      }
    }
    // If messageContentType is 'text/html', create MimeBodyPart and add it to email
    // message.
    if (messageContentType != null && messageContentType.equalsIgnoreCase(HTML_MSG_CONTENT_TYPE)) {
      mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(messageContent, "text/html; charset=utf-8");
      multipart.addBodyPart(mimeBodyPart);
      emailMessage.setContent(multipart);
    } else {
      // If messageContentType is 'text/plain', set message content as text to email
      // message.
      emailMessage.setText(messageContent);
    }

    // Encode and wrap the MIME message into a gmail message
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    emailMessage.writeTo(buffer);
    byte[] rawMessageBytes = buffer.toByteArray();
    return rawMessageBytes;
  }

  /**
   * Decodes Base64 String into byte[]
   * returns byte[]
   */
  private byte[] decodeString(String base64Message) {
    byte[] decodedContent = Base64.decodeBase64(base64Message);
    return decodedContent;
  }

  /**
   * Encodes binary data using a URL-safe variation of the base64 algorithm
   * returns String
   */
  private String encodeString(byte[] rawMessageBytes) {
    String encodedContent = Base64.encodeBase64URLSafeString(rawMessageBytes);
    return encodedContent;
  }
}
