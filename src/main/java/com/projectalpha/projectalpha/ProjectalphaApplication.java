package com.projectalpha.projectalpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ProjectalphaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectalphaApplication.class, args);
	}
}
