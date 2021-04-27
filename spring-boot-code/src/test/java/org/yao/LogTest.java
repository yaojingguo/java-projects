package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LogTest {
  private Logger log = LogManager.getLogger();

  @Test
  public void testLoggging() {
    log.info("an info message");
  }
}
