package com.staxrt.tutorial;

import com.staxrt.tutorial.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	@Autowired
	private EmailService senderService;

	public static void main(String[] args) {

	  SpringApplication.run(Application.class, args);
	}

}
