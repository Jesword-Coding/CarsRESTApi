package com.cars.CarsBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cars")
public class CarsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsBackendApplication.class, args);
	}
}
