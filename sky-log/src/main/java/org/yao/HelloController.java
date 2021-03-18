package org.yao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public String time() {
        logger.info("current time");
        LocalDate date = LocalDate.now();
        return date.toString();
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


