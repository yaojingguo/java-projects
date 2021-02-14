package rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncProducer {
  public static void main(String[] args) throws Exception {
    //Instantiate with a producer group name.
    DefaultMQProducer producer = new DefaultMQProducer("p_g_2");
    // Specify name server addresses.
    producer.setNamesrvAddr(Conf.NAMESRV_ADDR);
    //Launch the instance.
    producer.start();
    producer.setRetryTimesWhenSendAsyncFailed(0);

    int messageCount = 4;
    final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
    for (int i = 0; i < messageCount; i++) {
      try {
        final int index = i;
        Message msg = new Message(Conf.TOPIC_NAME,
            "TagA",
            "OrderID188",
            "Async Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.send(msg, new SendCallback() {
          @Override
          public void onSuccess(SendResult sendResult) {
            countDownLatch.countDown();
            System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
          }

          @Override
          public void onException(Throwable e) {
            countDownLatch.countDown();
            System.out.printf("%-10d Exception %s %n", index, e);
            e.printStackTrace();
          }
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    countDownLatch.await(5, TimeUnit.SECONDS);
    producer.shutdown();
  }
}