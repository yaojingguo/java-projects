package org.yao;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class Main {
  private static KafkaConsumer<String, String> createConsumer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "foo");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    return new KafkaConsumer<>(props);
  }

  private static void verifyAutoCommit() {
    try (KafkaConsumer<String, String> consumer = createConsumer(); ) {
      consumer.subscribe(Arrays.asList("apiTopic"));
      boolean done = false;
      while (true) {
        long seconds = 2;
        System.out.printf("polling for %d seconds\n", seconds);
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(seconds));
        System.out.printf("polled\n");
        if (records.count() > 0) {
          for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.offset() + ": " + record.value());
          }
        } else {
          System.out.println("got nothing");
        }
      }
    }
  }

  public static void main(String[] args) {
    verifyAutoCommit();
  }
}
