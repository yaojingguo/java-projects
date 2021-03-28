package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Used together with log4j2-mixed.xml.
 */
public class MixedLogTest {
  // Asynchronous logger
  private Logger log = LogManager.getLogger(getClass());
  // Root synchronous logger
  private Logger log2 = LogManager.getLogger("other.logger");

  @Test
  public void testLogging() {
    log.info("an info message");
    log.error("an error message");

    log2.info("an info message");
    log2.error("an info message");
  }
}
