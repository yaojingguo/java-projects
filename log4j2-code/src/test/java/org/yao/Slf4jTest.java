package org.yao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Test
  public void testUsage() {
    log.info("an info message");
  }
}
