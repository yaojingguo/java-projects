package org.yao.trace;


@Deprecated
public class Hi {
  public static void main(String[] args) throws InterruptedException {
    Hi h = new Hi();
    h.hi();
    h.hiWithName("xiaoyu");
    System.out.printf("toString: %s\n", h);
  }

  private void hi() throws InterruptedException {
    System.out.println("hi agent");
    Thread.sleep((long) (Math.random() * 500));
  }

  private void hiWithName(String name) {
    System.out.println("hi, " + name);
  }
}
