package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class ThreadMapUsage {
  private static Logger log = LogManager.getLogger(ThreadMapUsage.class);

  public static void top() {
    ThreadContext.put("id", "1");
    ThreadContext.put("name", "little rain");
    log.info("top message");
    ThreadContext.put("id", "2");
    log.info("top message");
    ThreadContext.remove("id");
    log.info("top message");
  }

  public static void main(String[] args) {
    top();
  }
}
