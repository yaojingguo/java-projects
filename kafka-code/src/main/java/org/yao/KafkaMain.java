package org.yao;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.net.InetAddress;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaMain {
  public static String topic = "quickstart-events";

  private static Properties producerConfig(String portNumber) throws Exception {
    Properties cfg = new Properties();
    cfg.put("client.id", InetAddress.getLocalHost().getHostName());
    cfg.put("bootstrap.servers", "localhost:" + portNumber);
    cfg.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    cfg.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    return cfg;
  }

  private static Properties consumerConfig(String portNumber) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:" + portNumber);
    props.put("group.id", "foo");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    return props;
  }

  public static void main(String[] args) throws Exception {
    KafkaProducer<String, String> kp = new KafkaProducer<String, String>(producerConfig());
    try {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "III");
      System.out.printf("sending...\n");
      Future<RecordMetadata> future = kp.send(record);
      future.get();
      System.out.printf("sent\n");
    } finally {
      kp.close();
    }

    KafkaConsumer<String, String> kc = new KafkaConsumer<String, String>(consumerConfig());
    kc.subscribe(Arrays.asList(topic));

    while (true) {
      ConsumerRecords<String, String> records = kc.poll(Duration.ofSeconds(5));
      int count = records.count();
      System.out.printf("polled %d records\n", count);
      if (count == 0) {
        System.out.printf("exit the loop");
        break;
      }

      for (ConsumerRecord<String, String> rec : records) {
        System.out.printf("key: %s, key: %s\n", rec.key(), rec.value());
      }
    }
    kc.close();
  }

  class KafkaThread extends Thread {
    @Override
    public void run() {

    }
  }

}
