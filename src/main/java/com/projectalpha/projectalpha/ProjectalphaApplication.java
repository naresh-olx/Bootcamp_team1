package com.projectalpha.projectalpha;

import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
public class ProjectalphaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectalphaApplication.class, args);
	}
}
