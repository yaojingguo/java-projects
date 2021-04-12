package org.yao.custom;

import net.bytebuddy.ByteBuddy;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Entry {
  public static void main(String[] args) throws Exception {
    SumExample sum =
        new ByteBuddy()
            .subclass(SumExample.class)
            .method(named("calculate"))
            .intercept(SumImplementation.INSTANCE)
            .make()
            .load(Entry.class.getClassLoader())
            .getLoaded()
            .newInstance();
    System.out.printf("sum result: %d\n", sum.calculate());
  }
}
