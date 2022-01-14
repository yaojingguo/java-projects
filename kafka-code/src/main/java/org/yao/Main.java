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

      for (int i = 0; i < 100; i++) {
        long seconds = 1;
        System.out.printf("%d: polling for %d seconds\n", i, seconds);
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(seconds));
        System.out.printf("polled %d records\n", records.count());
        if (records.count() > 0) {
          for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.offset() + ": " + record.value());
          }
        } else {
          System.out.printf("got nothing\n");
        }
        report(consumer);
      }

      for (int i = 0; i < 5; i++) {
        nap(3);
        report(consumer);
      }
    }
  }

  private static void report(KafkaConsumer<String, String> consumer) {
    Map<MetricName, ? extends Metric> map = consumer.metrics();
    for (Metric v: map.values()) {
      String name = "last-poll-seconds-ago";
      if (v.metricName().name().equals(name)) {
        System.out.printf("%s: %s\n", name, v.metricValue());
      }
    }
  }

  private static void nap(long seconds) {
    try {
      System.out.printf("sleeping for %d seconds...\n", seconds);
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      throw  new RuntimeException(e);
    }
  }


  public static void main(String[] args) {
    verifyAutoCommit();
  }
}
