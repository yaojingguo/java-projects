package rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
  public static void main(String[] args) throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("p_g_1");
    producer.setNamesrvAddr(Conf.NAMESRV_ADDR);
    producer.start();

    for (int i = 0; i < 4; i++) {
      Message msg =
          new Message(
              Conf.TOPIC_NAME,
              "TagA",
              ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */);

      SendResult sendResult = producer.send(msg);
      System.out.printf("%s%n", sendResult);
    }

    producer.shutdown();
  }
}
