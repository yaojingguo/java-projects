package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class AsyncLogTest {

  private Logger log = LogManager.getLogger(getClass());

  @Test
  public void testLogging() {
    log.info("an info message");
    log.error("an error message");
  }
}
