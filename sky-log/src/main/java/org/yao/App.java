package org.yao;

import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
  public static void main(String[] args) {
    prepare();
//    AbstractSpan span = ContextManager.createLocalSpan("prepare");
//    try {
//      init();
//    } finally {
//      ContextManager.stopSpan();
//    }
    SpringApplication.run(App.class, args);
  }

  private static void init() {
    System.out.println("init...");
  }

  @Trace
  private static void prepare() {
    System.out.println("preparing...");
  }
}
