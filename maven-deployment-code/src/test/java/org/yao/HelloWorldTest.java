package org.yao;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class HelloWorldTest {
  @Test
  public void testGreet() {
    assertThat(HelloWorld.sentence()).isEqualTo("hello, world");
  }
}
