package com.microcommerce.service_vehicules.repository;

import com.microcommerce.service_vehicules.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
}
