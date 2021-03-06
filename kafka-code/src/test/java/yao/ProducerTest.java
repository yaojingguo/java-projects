package yao;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

public class ProducerTest {

  public static String topic = "quickstart-events";

  // One run returns 9740ms for creating 2k topics.
  @Test
  public void testCreateTopics() throws Exception {
    List<NewTopic> topics = new ArrayList<>();
    int count = 2000;
    for (int i = 0; i < count; i++) {
      String topicName = String.format("2-k-%d", i);
      NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
      topics.add(newTopic);
    }

    AdminClient kac = AdminClient.create(Utils.producerConfig("1"));
    long before = System.currentTimeMillis();
    kac.createTopics(topics).all().get();
    System.out.printf("elapsed %d\n", System.currentTimeMillis() - before);
  }

  @Test
  public void testDeleteTopics() throws Exception {
    AdminClient kac = AdminClient.create(Utils.producerConfig("1"));
    List<String> topicNames = new ArrayList<>();
    int count = 1000;
    for (int i = 0; i < count; i++) {
      String topicName = String.format("many-%d", i);
      topicNames.add(topicName);
    }
    kac.deleteTopics(topicNames).all().get();
  }

  @Test
  public void testSend() throws Exception {
    try (KafkaProducer<String, String> kp = Utils.createProducer("1"); ) {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key1", "value70");
      Future<RecordMetadata> future = kp.send(record);
      System.out.println("writting...");
      future.get();
      System.out.println("written");
    }
  }

  private static void sendWithCallback() throws Exception {
    try (KafkaProducer<String, String> kp = Utils.createProducer("1"); ) {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key2", "value2");
      kp.send(
          record,
          new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
              System.out.printf("topic: %s\n", metadata.topic());
              System.out.printf("partition: %d\n", metadata.partition());
              System.out.printf("offset: %d\n", metadata.offset());
            }
          });
    }
  }

  public static void main(String[] args) throws Exception {
    Properties cfg = Utils.producerConfig("1");
    cfg.setProperty("retries", "1");
    cfg.setProperty("max.block.ms", "1000");
//    cfg.setProperty("buffer.memory", "26384");
    KafkaProducer<String, String> kp = new KafkaProducer<String, String>(cfg);
    try {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "133");
      System.out.printf("sending...\n");
      kp.send(record);
      System.out.printf("sent\n");
//      Thread.sleep(2000 * 5);
    } finally {
            kp.close();
    }
  }
}
