package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;
import org.junit.Test;

public class EcsTest {
  private Logger log = LogManager.getLogger("ecs.logger");

  @Test
  public void testEcs() {
    log.info(new StringMapMessage()
        .with("message", "Hello World!")
        .with("foo", "bar\""));
  }
}
