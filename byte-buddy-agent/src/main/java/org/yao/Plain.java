package org.yao;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;



public class Plain {
  public Plain() {
    Class<?> dynamicType = new ByteBuddy()
        .subclass(Object.class)
        .method(ElementMatchers.named("toString"))
        .intercept(FixedValue.value("Hello World!"))
        .make()
        .load(getClass().getClassLoader())
        .getLoaded();

//    assertThat(dynamicType.newInstance().toString(), is("Hello World!"));
  }
  public static void main(String[] args) {


  }
}
