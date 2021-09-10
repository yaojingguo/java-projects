package org.yao;

import com.google.common.flogger.FluentLogger;

public class Entry {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void main(String[] args) {
    logger.atInfo().log("Log message with: %s", "enter-classroom");
    logger.atInfo().log("info message");
  }
}
