package com.example.actuatorservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Greeting {

	private Logger log = LogManager.getLogger();

	private final double id;
	private final String content;

	public Greeting(double id, String content) {
		this.id = id;
		this.content = content;
	}

	public void init() {
		log.info("initializing greeting...");
	}

	public double getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}
