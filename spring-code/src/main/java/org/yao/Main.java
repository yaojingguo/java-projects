package org.yao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.yao.five.SystemTestConfig;
import org.yao.five.TransferService;

public class Main {

  public static void main(String[] args) {
    fiveWay();
  }

  private static void fiveWay() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);
    // everything wires up across configuration classes...
    TransferService transferService = ctx.getBean(TransferService.class);
    transferService.transfer(100.00, "A123", "C456");
  }

  private static void twoWay() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(MyServiceImpl.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
  }

  private static void oneWay() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
  }

  private static void threeWay() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.register(MyServiceImpl.class);
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
  }

  private static void fourWay() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);

    // now both beans A and B will be available...
    A a = ctx.getBean(A.class);
    B b = ctx.getBean(B.class);

    System.out.printf("A: %s\n", a);
    System.out.printf("B: %s\n", b);

    System.out.println(ctx.getBean("aa"));
  }
}
