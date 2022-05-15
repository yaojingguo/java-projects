package org.yao.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyAtmApplication {

  private static Logger LOGGER = LogManager.getLogger(MyAtmApplication.class);

  public static void main(String[] args) throws Exception {
    LOGGER.info("Starting ATM application");

    int amount1 = Integer.parseInt(args[1]);
    LOGGER.info("withdraw {}", amount1);
    MyAtm.withdrawMoney(amount1);

    long sleepSeconds = Long.valueOf(args[0]);
    LOGGER.info("sleeping {} seconds...", sleepSeconds);
    Thread.sleep(sleepSeconds * 1000);

    int amount2 = Integer.parseInt(args[2]);
    LOGGER.info("withdraw {}", amount2);
    MyAtm.withdrawMoney(Integer.parseInt(args[2]));
  }

}
