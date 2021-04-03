package com.assignment.jsonbase64diff;

import com.assignment.jsonbase64diff.repository.IJsonBase64Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IJsonBase64Repository.class)
public class Jsonbase64diffApplication {

	public static void main(String[] args) {
		SpringApplication.run(Jsonbase64diffApplication.class, args);
	}

}
