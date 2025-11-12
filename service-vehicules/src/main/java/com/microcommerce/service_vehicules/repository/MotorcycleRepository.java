package com.microcommerce.service_vehicules.repository;

import com.microcommerce.service_vehicules.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, String> {
}