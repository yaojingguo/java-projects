package org.yao;

import org.apache.skywalking.apm.toolkit.trace.Trace;

public class Worker {
  @Trace
  public void sleep() {
    System.out.printf("slept\n");
  }
}
