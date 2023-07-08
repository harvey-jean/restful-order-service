package com.jean.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*(scanBasePackages = {"com.jean.restful"})*/
public class RestfulOrderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulOrderApiApplication.class, args);
	}

}
