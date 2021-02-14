package org.yao;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ByteTest {

  @Test
  public void testByteBuddy() throws IllegalAccessException, InstantiationException {
    Class<?> dynamicType =
        new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello World!"))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
    assertThat(dynamicType.newInstance().toString()).isEqualTo("Hello World!");
  }
}
