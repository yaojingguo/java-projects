package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.yao.MyMessage.m;

public class LogTest {
  private Logger log = LogManager.getLogger();

  @Test
  public void testLoggging() {
    log.info("an info message");
  }

  @Test
  public void testStructured() {
    log.error(m("entered classroom", "cid", "10", "uid", "10"), new Throwable("abc"));
    log.error(m("entered classroom", new Throwable("def"), "cid", "10", "uid", "10"));
  }
}
