package com.algaworks.brewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ChecklistApplication {
	
	private static ApplicationContext APPLICATION_CONTEXT;
	/*
	public static void main(String[] args) {
		SpringApplication.run(ChecklistApplication.class, args);
	}
	*/
	
	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(ChecklistApplication.class, args);
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		return APPLICATION_CONTEXT.getBean(requiredType);
	}
	
}
