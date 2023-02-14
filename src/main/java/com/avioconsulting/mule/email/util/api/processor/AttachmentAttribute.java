package com.avioconsulting.mule.email.util.api.processor;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class AttachmentAttribute {
  @Parameter
  @Optional
  @DisplayName("Content Type")
  @Summary("Content type for the email attachment.")
  @Example("#[text/plain]")
  private String contentType;

  @Parameter
  @DisplayName("content")
  @Summary("Base64 encoded Content of the email attachment.")
  private String content;

  @Parameter
  @DisplayName("filename")
  @Summary("Filename of the email attachment.")
  private String filename;

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

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
}
