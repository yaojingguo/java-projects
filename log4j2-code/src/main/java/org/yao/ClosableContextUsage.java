package org.yao;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class ClosableContextUsage {
  private static Logger log = LogManager.getLogger(ClosableContextUsage.class);

  public static void top() {
    log.info("top");
    try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.push("123");) {
      log.info("block message with stack");
    }
    log.info("top");

    try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put("id", "999")
        .put("loginId", "100")) {
      log.info("block message with map");
    }
    log.info("top");

  }

  public static void main(String[] args) {
    ClosableContextUsage.top();
  }
}
