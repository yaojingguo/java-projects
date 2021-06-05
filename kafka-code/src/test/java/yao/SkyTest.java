package yao;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.WakeupException;
import org.junit.Test;

public class SkyTest {
  private static final String topic = "one";
  private static OkHttpClient client = new OkHttpClient();

  private static void http() throws IOException {
    String url = "https://httpbin.org/get";
    Request request = new Request.Builder().url(url).build();
    try (Response response = client.newCall(request).execute()) {
      String body = response.body().string();
      System.out.printf("body: %s\n", body);
    }
  }

  public static void testSend() throws Exception {
    KafkaProducer<String, String> producer = Utils.createProducer("p1");
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key1", "value70");
    Future<RecordMetadata> future = producer.send(record);
    System.out.println("writting...");
    future.get();
    System.out.println("written");
  }

  public static void testReceive() throws Exception {
    KafkaConsumer<String, String> consumer = Utils.createConsumer("group-1", "c1");
    try {
      consumer.subscribe(Arrays.asList(topic));
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
        http();
        if (records.count() == 0) {
          return;
        }
        for (ConsumerRecord<String, String> record : records) {
          Map<String, Object> map = new HashMap<>();
          map.put("partition", record.partition());
          map.put("offset", record.offset());
          map.put("value", record.value());
          System.out.printf("map: %s\n", map);
        }
      }
    } catch (WakeupException e) {
      // ignore for shutdown
    } finally {
      consumer.close();
    }
  }

  public static void main(String[] args) throws Exception {
//    testSend();
    testReceive();
  }
}
