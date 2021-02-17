package org.yao;

public class Log4JRunnable implements Runnable {
  private Transfer tx;
  private Log4JTransferService service;

  public Log4JRunnable(Transfer tx) {
    this.tx = tx;
  }

  @Override
  public void run() {
    System.out.println("running log4j runner");
    service.transfer(tx.getAmount());
  }
}
