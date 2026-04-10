package com.silentsignals.silentsignals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SilentsignalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SilentsignalsApplication.class, args);
	}

}
