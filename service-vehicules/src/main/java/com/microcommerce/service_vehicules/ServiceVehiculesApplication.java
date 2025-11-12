package com.microcommerce.service_vehicules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVehiculesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceVehiculesApplication.class, args);
	}

}
