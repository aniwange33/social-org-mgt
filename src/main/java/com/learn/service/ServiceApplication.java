package com.learn.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ServiceApplication.class, args);
		System.out.println("bean counts: "+ run.getBeanDefinitionCount());
	}

}
