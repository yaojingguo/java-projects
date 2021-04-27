package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Entry {
  private static Logger log = LogManager.getLogger();

  public static void main(String[] args) {
    log.info("entered class room", "cid", 10, "uid", 10);
  }
}
