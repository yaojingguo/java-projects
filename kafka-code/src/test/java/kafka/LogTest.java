package kafka;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Test
  public void testInfo() {
    log.info("info message");
  }
}
