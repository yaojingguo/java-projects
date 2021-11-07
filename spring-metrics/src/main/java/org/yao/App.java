package org.yao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;

@SpringBootApplication
@EnablePrometheusMetrics
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
