package org.yao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
  public static void main(String[] args)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = String.class.getMethod("valueOf", int.class);
    System.out.println("parameter count: " + method.getParameterCount());
    Object o = method.invoke(null, 11);
    System.out.println(o);
  }
}
