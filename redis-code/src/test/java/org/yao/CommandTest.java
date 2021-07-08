package org.yao;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

public class CommandTest {

  @Test
  public void testSetThenGet() {
    String key = "get-then-get";
    String message = "Hello, Redis!";
    // Set
    {
      RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
      StatefulRedisConnection<String, String> connection = redisClient.connect();
      RedisCommands<String, String> syncCommands = connection.sync();

      syncCommands.set(key, message);

      connection.close();
      redisClient.shutdown();
    }
    // Get
    {
      RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
      StatefulRedisConnection<String, String> connection = redisClient.connect();
      RedisCommands<String, String> syncCommands = connection.sync();

      System.out.println(syncCommands.get(key));

      connection.close();
      redisClient.shutdown();
    }
  }
}
