package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LogTest {
  private Logger logger = LogManager.getLogger(getClass());

  @Test
  public void testInfo() {
    logger.info("a info message");
  }
}
