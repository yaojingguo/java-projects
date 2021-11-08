package com.example.actuatorservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Greeting {

	private Logger log = LogManager.getLogger();

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public void init() {
		log.info("initializing greeting...");
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}
