package org.yao;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import io.lettuce.core.*;

import static com.google.common.truth.Truth.assertThat;


public class MultiTest {
  @Test
  public void multi() {
    String key1 = "key1";
    String key2 = "key2";

    {
      RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
      StatefulRedisConnection<String, String> connection = redisClient.connect();
      RedisCommands<String, String> redis = connection.sync();

      redis.set(key1, "1");
      redis.set(key2, "2");

      connection.close();
      redisClient.shutdown();
    }

    RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    RedisCommands<String, String> redis = connection.sync();

    assertThat(redis.get(key1)).isEqualTo("1");
    assertThat(redis.get(key2)).isEqualTo("2");


    redis.multi();
    redis.set(key1, "10");
    redis.set(key2, "20");
    redis.exec();

    assertThat(redis.get(key1)).isEqualTo("10");
    assertThat(redis.get(key2)).isEqualTo("20");

    connection.close();
    redisClient.shutdown();
  }

  @Test
  public void testWatch() {
    String key = "watch";
    RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    RedisCommands<String, String> redis = connection.sync();
    redis.watch(key);

    redis.multi();
    redis.set(key, "100");
    System.out.printf("exec: %s\n", redis.exec().get(0));
  }
}
