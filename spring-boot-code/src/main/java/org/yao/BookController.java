package org.yao;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

  @GetMapping("")
  public String index() {
    return "Two Cities\nThree nations";
  }

//  @GetMapping("send")
//  public String send() throws Exception {
//    KafkaCode.send();
//    return "kafka message sent";
//  }
}
