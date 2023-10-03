package com.fadgiras.dev5etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.fadgiras.dev5etl.*")
@EntityScan("com.fadgiras.dev5etl.*")
public class Dev5etlApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dev5etlApplication.class, args);
	}

}
