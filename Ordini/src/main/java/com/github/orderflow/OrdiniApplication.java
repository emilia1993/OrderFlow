package com.github.orderflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "com.github.orderflow.entity")
@EnableJpaRepositories(basePackages = "com.github.orderflow.repository")
@SpringBootApplication
@EnableScheduling
public class OrdiniApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdiniApplication.class, args);
	}
}




