package org.yao;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

public class ConsumerTest {

  private static volatile boolean running = true;

  private static KafkaConsumer<String, String> createConsumer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "foo");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    return new KafkaConsumer<>(props);
  }

  private static void nap(long millis) {
    try {
      System.out.printf("napping for %d milliseconds\n", millis);
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static void verifyAutoCommit() {
    try (KafkaConsumer<String, String> consumer = createConsumer(); ) {
      consumer.subscribe(Arrays.asList(ProducerTest.topic));
      nap(1000 * 300);
      while (running) {
        long seconds = 120;
        System.out.printf("polling for %d seconds\n", seconds);
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(120));
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

  @Test
  public void testAutoCommit() {
    verifyAutoCommit();
  }

  @Test
  public void testWakeup() {
    try (KafkaConsumer<String, String> consumer = createConsumer(); ) {
      Thread killer =
          new Thread() {
            @Override
            public void run() {
              try {
                Thread.sleep(20 * 1000);
                System.out.println("waking up...");
                consumer.wakeup();
              } catch (InterruptedException ie) {
                throw new RuntimeException();
              }
            }
          };
      killer.start();

      consumer.subscribe(Arrays.asList(ProducerTest.topic));
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<String, String> record : records)
          System.out.println(record.offset() + ": " + record.value());
      }
    } catch (WakeupException e) {
      System.out.println("woken");
    }
  }

  private static KafkaConsumer<String, String> manual() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "consumer-tutorial");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("enable.auto.commit", "false");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList(ProducerTest.topic));
    return consumer;
  }

  // at most once semantics
  @Test
  public void testAtLeastOnce() {
    KafkaConsumer<String, String> consumer = manual();
    try {

      while (running) {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records)
          System.out.println(record.offset() + ": " + record.value());
        if (records.count() > 0) {
          try {
            System.out.println("commit");
            consumer.commitSync();
          } catch (CommitFailedException e) {
            // application specific failure handling
          }
        }
      }
    } finally {
      consumer.close();
    }
  }

  @Test
  public void testAtMostOnce() {
    KafkaConsumer<String, String> consumer = manual();
    try {
      while (running) {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        if (records.count() > 0) {
          try {
            System.out.println("commit");
            consumer.commitSync();
          } catch (CommitFailedException e) {
            // application specific failure handling
          }
        }

        for (ConsumerRecord<String, String> record : records)
          System.out.println(record.offset() + ": " + record.value());
      }
    } finally {
      consumer.close();
    }
  }

  @Test
  public void testCommitOneRecord() {
    KafkaConsumer<String, String> consumer = manual();
    try {
      while (running) {
        ConsumerRecords<String, String> records = consumer.poll(1000);

        try {
          for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.offset() + ": " + record.value());
            consumer.commitSync(
                Collections.singletonMap(
                    new TopicPartition(record.topic(), record.partition()),
                    new OffsetAndMetadata(record.offset() + 1)));
          }
        } catch (CommitFailedException e) {
          // application specific failure handling
        }
      }
    } finally {
      consumer.close();
    }
  }

  @Test
  public void testCommitOnePartition() {
    KafkaConsumer<String, String> consumer = manual();
    try {
      while (running) {
        ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
        for (TopicPartition partition : records.partitions()) {
          List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
          for (ConsumerRecord<String, String> record : partitionRecords)
            System.out.println(record.offset() + ": " + record.value());

          long lastoffset = partitionRecords.get(partitionRecords.size() - 1).offset();
          consumer.commitSync(
              Collections.singletonMap(partition, new OffsetAndMetadata(lastoffset + 1)));
        }
      }
    } finally {
      consumer.close();
    }
  }

  @Test
  public void testCommitAsync() {
    KafkaConsumer<String, String> consumer = manual();
    try {
      while (running) {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records)
          System.out.println(record.offset() + ": " + record.value());

        consumer.commitAsync(
            new OffsetCommitCallback() {
              @Override
              public void onComplete(
                  Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                if (exception != null) {
                  // application specific failure handling
                }
              }
            });
      }
    } finally {
      consumer.close();
    }
  }

  public static void main(String[] args) {
    verifyAutoCommit();
  }
}
