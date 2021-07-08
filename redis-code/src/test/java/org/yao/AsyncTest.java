package org.yao;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.junit.Test;

import org.junit.Assert;

import static com.google.common.truth.Truth.assertThat;

public class AsyncTest {
  @Test
  public void test() throws Exception {
    RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
    StatefulRedisConnection<String, String> connection = redisClient.connect();

    RedisAsyncCommands<String, String> asyncCmds = connection.async();
    RedisFuture<String> future = asyncCmds.get("a-key");
    String value = future.get();
    System.out.printf("value: %s\n", value);

    connection.close();
    redisClient.shutdown();
  }
  @Test
  public void testAssert() {
    System.out.printf("start\n");
    assertThat(1).isEqualTo(0);
    System.out.printf("continue\n");
    assertThat(2).isEqualTo(0);
  }

  @Test
  public void testJunit() {
    Assert.assertEquals(1, 2);
    System.out.printf("continue\n");
    Assert.assertEquals(10, 2);
  }
}
