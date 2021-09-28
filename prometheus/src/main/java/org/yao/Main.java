package org.yao;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import java.io.IOException;

public class Main {
  private static Counter oneCounter =
      Counter.build().name("one_counter").help("No.1 counter").register();

  public static void main(String[] args) throws IOException {
    HTTPServer server = new HTTPServer(1234);

    DefaultExports.initialize();

    new Thread(() -> {
      for (;;) {
        oneCounter.inc();
        nap(2);
      }
    }).start();
  }

  private static void nap(long seconds) {
    try {
      System.out.printf("napping %d seconds\n", seconds);
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
}
