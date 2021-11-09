package com.example.actuatorservice;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Random;

public class Entry {

  public static void main(String[] args) {
    System.out.printf("in main\n");
    final PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    System.out.printf("created registry\n");
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      server.createContext("/prometheus", httpExchange -> {
        String response = registry.scrape();
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = httpExchange.getResponseBody()) {
          os.write(response.getBytes());
        }
      });

//      new Thread(server::start).start();
      server.start();
      System.out.printf("HTTP server started\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    new Thread(() -> metrics(registry)).start();
    System.out.printf("metrics started\n");
  }

  private static Random random = new Random();

  private static void metrics(PrometheusMeterRegistry registry) {
    Counter counter = registry.counter("roombox_requests_total");

    for (;;) {
      counter.increment();
      Util.snap(random.nextInt(2000));
    }
  }
}
