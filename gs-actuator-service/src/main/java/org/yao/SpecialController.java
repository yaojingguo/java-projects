package org.yao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("special")
public class SpecialController {
  private Logger log = LoggerFactory.getLogger(SpecialController.class);

  public static final String HEADER_KEY = "roombox_code";

  @GetMapping("index")
  public String index(@RequestParam long millis, @RequestParam boolean success) {
    if (!success) {
      throw new RuntimeException();
    }
    log.info("index: sleeping for {} milliseconds...", millis);
    Util.snap(millis);
    log.info("index: woke up");
    return "index: I am special";
  }

  @GetMapping("index2")
  public String index2(@RequestParam long millis, @RequestParam boolean success) {
    if (!success) {
      throw new RuntimeException();
    }
    log.info("index2: sleeping for {} milliseconds...", millis);
    Util.snap(millis);
    log.info("index2: woke up");
    return "index2: I am special";
  }

  @GetMapping("header")
  public String header(HttpServletResponse response) {
    response.addHeader(HEADER_KEY, "211");
    return "{\"success\": true}";
  }
}
