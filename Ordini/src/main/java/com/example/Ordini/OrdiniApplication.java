package com.example.Ordini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "com.example.Ordini.entity")  // Assicurati che il pacchetto delle entità sia corretto
@EnableJpaRepositories(basePackages = "com.example.Ordini.repository") // Specifica il pacchetto dei repository
@SpringBootApplication
@EnableScheduling
public class OrdiniApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdiniApplication.class, args);
	}
}




