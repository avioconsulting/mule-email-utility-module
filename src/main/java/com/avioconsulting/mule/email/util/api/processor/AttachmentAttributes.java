package com.avioconsulting.mule.email.util.api.processor;

import java.util.ArrayList;
import java.util.List;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class AttachmentAttributes {
  @Parameter
  @DisplayName("Attachment Attributes")
  @Optional
  @NullSafe
  @Summary("Attachment that you want to send in the email")
  private List<AttachmentAttribute> attachmentAttributes;

  public AttachmentAttributes() {
    this.attachmentAttributes = new ArrayList<>();
  }

  public List<AttachmentAttribute> getAttributeList() {
    return this.attachmentAttributes;
  }

}
