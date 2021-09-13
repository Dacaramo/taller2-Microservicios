package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CalculadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculadoraApplication.class, args);
	}

}
