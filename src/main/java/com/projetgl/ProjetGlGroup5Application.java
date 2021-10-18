package com.projetgl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = ProjetGlGroup5Application.class)
@EntityScan(basePackageClasses = ProjetGlGroup5Application.class)
@EnableTransactionManagement
public class ProjetGlGroup5Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetGlGroup5Application.class, args);
	}

}
