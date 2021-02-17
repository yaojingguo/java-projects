package org.yao;

import org.apache.logging.log4j.ThreadContext;

public class Log4JRunnable implements Runnable {
  private Transfer tx;
  private Log4JTransferService service = new Log4JTransferService();

  public Log4JRunnable(Transfer tx) {
    this.tx = tx;
  }

  @Override
  public void run() {
//    System.out.println("log4j runner begin");

    ThreadContext.put("transaction.id", tx.getTransactionId());
    ThreadContext.put("transaction.owner", tx.getSender());
    service.transfer(tx.getAmount());
    ThreadContext.clearAll();
//    System.out.println("log4j runner ended");
  }
}
