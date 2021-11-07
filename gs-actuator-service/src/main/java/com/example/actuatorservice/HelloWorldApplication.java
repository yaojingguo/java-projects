package com.example.actuatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

@SpringBootApplication
public class HelloWorldApplication {

	public static void main(String[] args) {
		System.out.println( ansi().fg(RED).a("Hello").fg(GREEN).a(" World").reset() );
		SpringApplication.run(HelloWorldApplication.class, args);
	}

}
