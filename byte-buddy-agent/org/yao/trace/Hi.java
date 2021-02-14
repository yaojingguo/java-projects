package org.yao.trace;

public class Hi {
  public static void main(String[] args) throws InterruptedException {
    Hi apiTest = new Hi();
    apiTest.hi();
  }

  private void hi() throws InterruptedException {
    System.out.println("hi agent");
    Thread.sleep((long) (Math.random() * 500));
  }

}
