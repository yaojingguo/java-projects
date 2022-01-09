package org.yao;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
  public static void main(String[] args) {
    PrometheusMeterRegistry prometheusRegistry =
        new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

      final Counter counter = prometheusRegistry.counter("xiao_counter", "age", "12");
      new Thread(
              () -> {
                System.out.printf("incrementing...\n");
                counter.increment();
                System.out.printf("incremented\n");
              })
          .start();

      MyCounterState state = new MyCounterState();

      FunctionCounter functionCounter =
          FunctionCounter.builder("yu_counter", state, obj -> obj.count())
              .description("a description of what this counter does") // optional
              .tags("region", "test") // optional
              .register(prometheusRegistry);

      server.createContext(
          "/prometheus",
          httpExchange -> {
            String response = prometheusRegistry.scrape();
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = httpExchange.getResponseBody()) {
              os.write(response.getBytes());
            }
          });

      new Thread(server::start).start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void nap(long seconds) {
    try {
      Thread.sleep(1000 * seconds);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
