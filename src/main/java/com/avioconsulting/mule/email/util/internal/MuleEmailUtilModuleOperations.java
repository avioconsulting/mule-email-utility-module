package com.avioconsulting.mule.email.util.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import com.avioconsulting.mule.email.util.api.processor.EmailProperties;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
   * Example of an operation that uses the configuration and a connection instance
   * to perform some action.
   */
  @DisplayName("Generate Email Content")
  @MediaType(value = ANY, strict = false)
  public String generateEmailContent(@ParameterGroup(name = "Email Info") EmailProperties emailProperties) {
    String encodedEmail = "";
    try {
      byte[] emailContent = createEmailContent(emailProperties);
      encodedEmail = encodeString(emailContent);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // String encodedEmail = encodeString(emailContent);
    return encodedEmail;
  }

  private byte[] createEmailContent(EmailProperties emailProperties) throws MessagingException, IOException {
    String fromAddress = emailProperties.getFrom();
    String toAddress = emailProperties.getTo();
    String ccAddress = emailProperties.getCc();
    String bccAddress = emailProperties.getBcc();
    String messageSubject = emailProperties.getSubject();
    String messageContentType = emailProperties.getContentType();
    String messageContent = emailProperties.getContent();

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
    if (messageContentType != null && messageContentType.equalsIgnoreCase(HTML_MSG_CONTENT_TYPE)) {
      Multipart multipart = new MimeMultipart();
      MimeBodyPart htmlPart = new MimeBodyPart();
      htmlPart.setContent(messageContent, "text/html; charset=utf-8");
      multipart.addBodyPart(htmlPart);
      emailMessage.setContent(multipart);
    } else {
      emailMessage.setText(messageContent);
    }

    // Encode and wrap the MIME message into a gmail message
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    emailMessage.writeTo(buffer);
    byte[] rawMessageBytes = buffer.toByteArray();
    return rawMessageBytes;
  }

  private String encodeString(byte[] rawMessageBytes) {
    String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
    return encodedEmail;
  }
}
