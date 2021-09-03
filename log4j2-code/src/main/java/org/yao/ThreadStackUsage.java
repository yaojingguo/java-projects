package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class ThreadStackUsage {
  private static Logger log = LogManager.getLogger(ThreadStackUsage.class);

  public static void top() {
    ThreadContext.push("top");
    log.info("work message before");
    a();
    log.info("work message");
    log.info("peek={}", ThreadContext.peek());
    ThreadContext.pop();
  }

  private static void a() {
    ThreadContext.push("a");
    log.info("a message before");
    b();
    log.info("a message after");
    ThreadContext.pop();
  }

  private static void b() {
    ThreadContext.push("b");
    log.info("b message before");
    c();
    log.info("b message after");
    ThreadContext.pop();
  }

  private static void c() {
    ThreadContext.push("c");
    log.info("c message");
    ThreadContext.pop();
  }

  public static void main(String[] args) {
    top();
  }
}
