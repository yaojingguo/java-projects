package org.yao;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class HelloWorldController {

  private static final String template = "Hello, %s!";

  private Counter counter;

  @Autowired private JdbcTemplateDao dao;

  @Autowired
  public void init(PrometheusMeterRegistry registry) {
    System.out.printf("registry: %s\n", registry);
    this.counter = registry.counter("roombox_greet_request_total");
  }

  @GetMapping("/hello-world")
  @ResponseBody
  public Greeting sayHello(
      @RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
    counter.increment();
    return new Greeting(counter.count(), String.format(template, name));
  }

  @GetMapping("/classes")
  @ResponseBody
  public List classes() {
    return dao.classes();
  }

  @GetMapping("/start")
  @ResponseBody
  public String start() {
    return dao.stress();
  }

  @GetMapping("/hold")
  @ResponseBody
  public String hold() {
    return dao.holdConnections();
  }
}
