package org.yao;

import org.apache.logging.log4j.ThreadContext;

public class TransferRunnable implements Runnable {
  private Transfer transfer;
  private TransferService service = new TransferService();

  public TransferRunnable(Transfer transfer) {
    this.transfer = transfer;
  }

  @Override
  public void run() {
    ThreadContext.put("transaction.id", transfer.getTransactionId());
    ThreadContext.put("transaction.owner", transfer.getSender());
    service.transfer(transfer);
    ThreadContext.clearAll();
  }
}
