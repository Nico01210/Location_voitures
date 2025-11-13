package com.microcommerce.service_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceReservationApplication.class, args);
	}
}
