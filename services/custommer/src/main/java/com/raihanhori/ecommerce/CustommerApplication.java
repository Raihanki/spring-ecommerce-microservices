package com.raihanhori.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CustommerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustommerApplication.class, args);
	}

}
