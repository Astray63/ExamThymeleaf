package com.humanbooster.examthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.humanbooster.services", "com.humanbooster.examthymeleaf"})
public class ExamthymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamthymeleafApplication.class, args);
	}

}
