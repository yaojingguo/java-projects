package org.yao.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyAtm {
  private static Logger LOGGER = LogManager.getLogger(MyAtm.class);

  private static final int account = 10;

  public static void withdrawMoney(int amount) throws InterruptedException {
    Thread.sleep(2000l); //processing going on here
    LOGGER.info("Successful Withdrawal of [{}] units!", amount);
  }
}

