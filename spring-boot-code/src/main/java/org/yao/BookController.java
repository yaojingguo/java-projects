package org.yao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

  private Logger log = LogManager.getLogger();

  @GetMapping("")
  public String index() {
    log.info("running index API", "cid", 10, "uid", 20);
    return "Two Cities\nThree nations";
  }
}
