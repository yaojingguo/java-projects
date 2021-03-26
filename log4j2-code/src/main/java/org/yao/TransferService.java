package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransferService {
  private Logger logger = LogManager.getLogger(getClass());

  public boolean transfer(Transfer transfer) {
    beforeTransfer(transfer.getAmount());
    // Do the transfer
    afterTransfer(transfer.getAmount(), true);
    return true;
  }

  protected void beforeTransfer(long amount) {
    logger.info("preparing to transfer $" + amount + ".");
  }

  protected void afterTransfer(long amount, boolean outcome) {
    logger.info("transferred of $" + amount + " result: " + outcome + ".");
  }
}
