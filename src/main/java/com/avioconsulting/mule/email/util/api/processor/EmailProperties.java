package com.avioconsulting.mule.email.util.api.processor;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class EmailProperties {
  @Parameter
  @Optional
  @DisplayName("From")
  @Summary("'From' email address used to deliver the message.")
  @Example("#[avio-sender@dev.avioconsulting.com]")
  private String from;

  @Parameter
  @DisplayName("To")
  @Summary("'To' email address used to deliver the message.")
  @Example("#[avio-to@dev.avioconsulting.com]")
  private String to;

  @Parameter
  @Optional
  @DisplayName("cc")
  @Summary("'cc' email address used to deliver the message.")
  @Example("#[avio-cc@dev.avioconsulting.com]")
  private String cc;

  @Parameter
  @Optional
  @DisplayName("bcc")
  @Summary("'bcc' email address used to deliver the message.")
  @Example("#[avio-bcc@dev.avioconsulting.com]")
  private String bcc;

  @Parameter
  @DisplayName("Subject")
  @Summary("Subject of email message.")
  private String subject;

  @Parameter
  @DisplayName("Content Type")
  @Summary("Content type for the email message.")
  @Example("#[text/plain]")
  private String contentType;

  @Parameter
  @DisplayName("content")
  @Summary("Content of the email message.")
  private String content;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getCc() {
    return cc;
  }

  public void setCc(String cc) {
    this.cc = cc;
  }

  public String getBcc() {
    return bcc;
  }

  public void setBcc(String bcc) {
    this.bcc = bcc;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
