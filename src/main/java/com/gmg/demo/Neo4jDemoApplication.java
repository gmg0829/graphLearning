package com.gmg.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Neo4jDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Neo4jDemoApplication.class, args);
	}

}

