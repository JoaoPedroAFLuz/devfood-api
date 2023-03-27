package com.joaopedroluz57.devfood;

import com.joaopedroluz57.devfood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class DevfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevfoodApiApplication.class, args);
	}

}
