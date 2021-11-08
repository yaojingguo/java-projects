package com.example.actuatorservice;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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
	private final AtomicLong counter = new AtomicLong();


	@Autowired
	private JdbcTemplateDao dao;

	@GetMapping("/hello-world")
	@ResponseBody
	public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
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
}
