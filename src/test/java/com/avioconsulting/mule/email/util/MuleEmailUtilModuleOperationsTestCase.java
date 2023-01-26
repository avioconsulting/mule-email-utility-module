package com.avioconsulting.mule.email.util;

import org.junit.Assert;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

public class MuleEmailUtilModuleOperationsTestCase extends MuleArtifactFunctionalTestCase {
  /**
   * Specifies the mule config xml with the flows that are going to be executed in
   * the tests, this file lives in the test resources.
   */
  @Override
  protected String getConfigFile() {
    return "test-email-utility-config.xml";
  }

  @Test
  public void executeTestGenerateEmail() throws Exception {
    String payloadValue = ((String) flowRunner("generate-email-content-test").run().getMessage().getPayload()
        .getValue());
    Assert.assertNotNull(payloadValue);
  }
}
