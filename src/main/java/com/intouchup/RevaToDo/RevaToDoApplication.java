package com.intouchup.RevaToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing//to enable auto date modification where applicable
public class RevaToDoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevaToDoApplication.class, args);
	}

}
