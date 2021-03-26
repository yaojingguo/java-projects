package org.yao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  private Logger logger = LogManager.getLogger(getClass());

  private List<String> names = new ArrayList<>();

  @GetMapping("/greeting")
  public String greeting() {
    logger.info("greeting people");
    return "hello, world";
  }

  @GetMapping("/time")
  public String time() throws Exception {
    logger.info("current time");
    System.out.printf("trace id: %s\n", TraceContext.traceId());
    work();
    LocalDate date = LocalDate.now();
    return date.toString();
  }

  @Trace
  private void work() throws Exception {
    Thread.sleep(1000);
  }

  @PostMapping("/name")
  public String addPerson(@RequestBody String name) {
    names.add(name);
    return name;
  }

  @GetMapping("/name")
  public String person() {
    return names.toString();
  }
}