package com.abdproject.gestionstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestionstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionstockApplication.class, args);
	}

}
