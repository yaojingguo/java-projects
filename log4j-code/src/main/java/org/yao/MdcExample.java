package org.yao;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class MdcExample {
  private static Logger log = Logger.getLogger(MdcExample.class);

  public static void main(String[] args) {
    MDC.put("ip", "127.0.0.1");
    log.info("a info message");
  }
}
