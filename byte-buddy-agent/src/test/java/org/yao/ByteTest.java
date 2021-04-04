package org.yao;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteTest {

  @Test
  public void testByteBuddy() throws IllegalAccessException, InstantiationException {
    Class<?> dynamicType =
        new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString"))
            .intercept(FixedValue.value("Hello World!"))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
    assertThat(dynamicType.newInstance().toString()).isEqualTo("Hello World!");
  }

  @Test
  public void testCreateClass() {
    {
      DynamicType.Unloaded<?> dynamicType = new ByteBuddy().subclass(Object.class).make();
      System.out.printf("dynamic type: %s\n", dynamicType);
    }
    {
      DynamicType.Unloaded<?> dynamicType =
          new ByteBuddy().subclass(Object.class).name("example.Type").make();
      System.out.printf("dynamic type: %s\n", dynamicType);
    }
    {
      DynamicType.Unloaded<?> dynamicType =
          new ByteBuddy()
              .with(
                  new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                      return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                  })
              .subclass(Object.class)
              .make();
      System.out.printf("dynamic type: %s\n", dynamicType);
    }
  }

  @Test
  public void testClassLoading() {
    Class<?> type =
        new ByteBuddy()
            .subclass(Object.class)
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
    // Null means the bootstrap class loader.
    System.out.printf("bootstrap class loader: %s\n", Object.class.getClassLoader());
    System.out.printf("system class loader: %s\n", getClass().getClassLoader());
  }

  @Test
  public void testClassReloading() {

  }

  @Test
  public void testFieldAndMethod() throws Exception {
    {
      String toString = new ByteBuddy()
          .subclass(Object.class)
          .name("example.Type")
          .make()
          .load(getClass().getClassLoader())
          .getLoaded()
          .newInstance() // Java reflection API
          .toString();
      System.out.printf("toString: %s\n", toString);
      System.out.printf("Object.toString: %s\n", new Object().toString());
    }
    {
      String fixedValue = "Hello World!";
      String toString = new ByteBuddy()
          .subclass(Object.class)
          .name("example.Type")
          .method(named("toString")).intercept(FixedValue.value(fixedValue))
          .make()
          .load(getClass().getClassLoader())
          .getLoaded()
          .newInstance()
          .toString();
      assertThat(toString).isEqualTo(fixedValue);
    }
  }
}
class Foo {
  String m() { return "foo"; }
}
class Bar {
  String m() { return "bar"; }
}

class A {
  void foo(Object o) {}
  void foo(Integer i) {}
}