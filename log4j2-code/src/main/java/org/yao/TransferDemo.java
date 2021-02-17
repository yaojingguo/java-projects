package org.yao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransferDemo {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(3);
    for (int i = 1; i <= 10; i++) {
      Transfer tx = new Transfer("tid-" + i, "jingguo", "xiaoyu", i);
      Runnable task = new Log4JRunnable(tx);
      executor.submit(task);
    }
    executor.shutdown();
  }
}
