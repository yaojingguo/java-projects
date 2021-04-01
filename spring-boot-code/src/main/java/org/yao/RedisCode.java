package org.yao;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;


public class RedisCode {
  public static void main(String[] args) throws Throwable {
    int seconds = 60;
    System.out.printf("sleeping for %d seconds...\n", seconds);
    Thread.sleep(seconds* 1000);
    accessRedis();
  }

  private static void accessRedis() throws Throwable {
    RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    RedisCommands<String, String> syncCommands = connection.sync();

    String value = syncCommands.get("mykey");

    System.out.println();
    System.out.printf("value: %s\n", value);
    System.out.println();

    connection.close();
    redisClient.shutdown();
  }
}
