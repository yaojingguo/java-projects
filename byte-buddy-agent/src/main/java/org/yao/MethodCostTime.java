package org.yao;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodCostTime {
  @RuntimeType
  public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable)
      throws Exception {
    long start = System.currentTimeMillis();
    try {
      System.out.println("calling");
      Object result = callable.call();
      System.out.println("called");
      return result;
    } finally {
      System.out.println(method + " method costed " + (System.currentTimeMillis() - start) + "ms");
    }
  }
}
