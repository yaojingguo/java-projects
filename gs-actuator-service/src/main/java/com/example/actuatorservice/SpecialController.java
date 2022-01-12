package com.example.actuatorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("special")
public class SpecialController {
  private Logger log = LoggerFactory.getLogger(SpecialController.class);

  @GetMapping("index")
  public String index(@RequestParam long seconds, @RequestParam boolean success) {
    if (!success) {
      throw new RuntimeException();
    }
    long millis = seconds * 1000;
    log.info("sleeping for {} seconds...", seconds);
    Util.snap(millis);
    log.info("woke up");
    return "I am special";
  }
}
