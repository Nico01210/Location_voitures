package com.microcommerce.service_vehicules.repository;
import com.microcommerce.service_vehicules.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

}