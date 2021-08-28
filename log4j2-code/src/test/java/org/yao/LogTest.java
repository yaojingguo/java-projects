package org.yao;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;

public class LogTest {
  private Logger log = LogManager.getLogger(getClass());
  private Logger jsonLog = LogManager.getLogger("json.logger");
  private Logger jsonTemplateLog = LogManager.getLogger("json.template.logger");

  @Test
  public void testLogging() {
    log.info("an info level message");
    doWork();
  }

  private void doWork() {
    log.info("working");
  }

  @Test
  public void testTransfer() throws Throwable {
    ExecutorService executor = Executors.newFixedThreadPool(3);
    for (int i = 1; i <= 3; i++) {
      Transfer tx = new Transfer("tid-" + i, "jingguo", "xiaoyu", i);
      Runnable task = new TransferRunnable(tx);
      executor.submit(task);
    }
    executor.shutdown();
    boolean result = executor.awaitTermination(3, TimeUnit.SECONDS);
    assertThat(result).isTrue();
  }

  @Test
  public void testContextStack() {
    ThreadStackUsage.top();
  }

  @Test
  public void testContextMap() {
    ThreadMapUsage.top();
  }

  @Test
  public void tetJsonLogging() {
//    log.info("an info message");
    jsonLog.info("entered classroom cid=10 uid=20");
  }

  @Test
  public void testLogException() {
    try {
      a();
    } catch (Throwable t) {
      log.error("failed to run a()", t);
    }
  }


  private void a() {
    b();
  }

  private void b() {
    c();
  }

  private void c() {
    throw new RuntimeException("exception message");
  }

  @Test
  public void testJsonTemplateLogging() {
        ThreadContext.put("name", "xiaoyu");
        jsonTemplateLog.info("entered classroom");
    jsonTemplateLog.error("error message", new Throwable("bad thing"));

//    new Throwable().printStackTrace();
    //    Map map = new HashMap();
    //    map.put("msg", "enterec classroom");
    //    map.put("cid", "10");
    //    map.put("uid", 1);
    //    jsonTemplateLog.info(map);
  }
}
