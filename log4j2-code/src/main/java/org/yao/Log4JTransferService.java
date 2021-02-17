package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4JTransferService extends TransferService {
  private Logger logger = LogManager.getLogger(getClass());

  @Override
  protected void beforeTransfer(long amount) {
    logger.info("Preparing to transfer " + amount + "$.");
  }

  @Override
  protected void afterTransfer(long amount, boolean outcome) {
    logger.info(
        "Has transfer of " + amount + "$ completed successfully ? " + outcome + ".");
  }
}