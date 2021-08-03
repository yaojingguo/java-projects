package org.yao;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaMain {
  public static String topic1 = "one";
  public static String topic2 = "two";

  private static Properties producerConfig(String portNumber, String clientId) throws Exception {
    Properties cfg = new Properties();
    cfg.put("client.id", clientId);
    cfg.put("bootstrap.servers", "localhost:" + portNumber);
    cfg.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    cfg.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    return cfg;
  }

  private static Properties consumerConfig(String portNumber, String groupId) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:" + portNumber);
    props.put("group.id", groupId);
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    return props;
  }

  public static void main(String[] args) throws Exception {
//    KafkaThread t = new KafkaThread();
//    t.start();
    sendAndPoll("9092", "clientId1", "groupId1", topic1);
//    t.join();
  }

  public static void sendAndPoll(String portNumber, String clientId, String groupId, String topic)
      throws Exception {
    KafkaProducer<String, String> kp =
        new KafkaProducer<String, String>(producerConfig(portNumber, clientId));
    try {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "III");
      System.out.printf(Thread.currentThread().getName() + ":sending...\n");
      Future<RecordMetadata> future = kp.send(record);
      future.get();
      System.out.printf(Thread.currentThread().getName() + ":sent\n");
    } finally {
      kp.close();
    }

    KafkaConsumer<String, String> kc =
        new KafkaConsumer<String, String>(consumerConfig(portNumber, groupId));
    kc.subscribe(Arrays.asList(topic));

    while (true) {
      ConsumerRecords<String, String> records = kc.poll(Duration.ofSeconds(5));
      int count = records.count();
      System.out.printf(Thread.currentThread().getName() + ": polled %d records\n", count);
      if (count == 0) {
        System.out.printf(Thread.currentThread().getName() + ":exit the loop");
        break;
      }

      for (ConsumerRecord<String, String> rec : records) {
        System.out.printf(
            Thread.currentThread().getName() + ":key: %s, key: %s\n", rec.key(), rec.value());
      }
    }
    kc.close();
  }

  static class KafkaThread extends Thread {
    @Override
    public void run() {
      try {
        sendAndPoll("9093", "clientId2", "groupId2", topic2);
      } catch (Throwable t) {
        throw new RuntimeException(t);
      }
    }
  }
}
