package dev.bbzblit.m120;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class M120Application {

	public static void main(String[] args) {
		SpringApplication.run(M120Application.class, args);
	}

}
