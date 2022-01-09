package org.yao;

public class MyCounterState {
  private double val = 1.0;
  public double count() {
    System.out.printf("function executed\n");
    return val++;
  }
}
