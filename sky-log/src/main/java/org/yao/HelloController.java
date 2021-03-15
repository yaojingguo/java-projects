package org.yao;

import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private Logger logger = LogManager.getLogger(getClass());

    @GetMapping("/greeting")
    public String greeting() {
        logger.info("greeting people");
        return "hello, world";
    }

    @GetMapping("/time")
    public String time() {
        logger.info("current time");
        LocalDate date = LocalDate.now();
        return date.toString();
    }
}


