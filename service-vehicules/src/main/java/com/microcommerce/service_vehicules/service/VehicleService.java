package com.microcommerce.service_vehicules.service;
import com.microcommerce.service_vehicules.model.Vehicle;
import com.microcommerce.service_vehicules.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    public List<Vehicle> findAll() {
        return repository.findAll(); }
    public Vehicle findById(String id) {
        return repository.findById(id) .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }
    public Vehicle save(Vehicle vehicle) {
        return repository.save(vehicle);
    }
}
