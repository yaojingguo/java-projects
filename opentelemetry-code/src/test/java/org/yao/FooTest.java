package org.yao;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class FooTest {
  @Test
  public void test() {
    System.out.printf("hello, world\n");
    assertThat(1).isEqualTo(1);
  }
}
