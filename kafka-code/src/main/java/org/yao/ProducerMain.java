package org.yao;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.net.InetAddress;
import java.util.Properties;

public class ProducerMain {
  public static String topic = "quickstart-events";

  private static Properties config() throws Exception {
    Properties cfg = new Properties();
    cfg.put("client.id", InetAddress.getLocalHost().getHostName());
    // 10.201.60.78
//    cfg.put("bootstrap.servers", "10.201.60.78:9092");
    cfg.put("bootstrap.servers", "localhost:9092");
    cfg.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    cfg.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    return cfg;
  }


  public static void main(String[] args) throws Exception {
    Properties cfg = config();
    cfg.setProperty("retries", "1");
    cfg.setProperty("max.block.ms", "1000");
//    cfg.setProperty("buffer.memory", "26384");
    KafkaProducer<String, String> kp = new KafkaProducer<String, String>(cfg);
    try {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "000");
      System.out.printf("sending...\n");
      kp.send(record);
      System.out.printf("sent\n");
//      Thread.sleep(2000 * 5);
    } finally {
      kp.close();
    }
  }
}
