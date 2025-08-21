package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SmartEmailAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartEmailAssistantApplication.class, args);
	}

	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
	    return builder.baseUrl("https://generativelanguage.googleapis.com")
	                  .build();
	}

}
