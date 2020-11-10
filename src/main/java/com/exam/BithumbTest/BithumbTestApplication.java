package com.exam.BithumbTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BithumbTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BithumbTestApplication.class, args);
	}

}
