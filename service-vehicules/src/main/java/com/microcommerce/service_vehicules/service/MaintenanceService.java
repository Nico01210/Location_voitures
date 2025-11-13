package com.microcommerce.service_vehicules.service;

import com.microcommerce.service_vehicules.model.MaintenanceTask;
import com.microcommerce.service_vehicules.model.Vehicle;
import com.microcommerce.service_vehicules.repository.MaintenanceTaskRepository;
import com.microcommerce.service_vehicules.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
    private final MaintenanceTaskRepository repository;
    private final VehicleRepository vehicleRepo;

    public List<MaintenanceTask> getTasksByVehicle(String registration) {
        return repository.findByVehicle_Registration(registration);
    }

    public MaintenanceTask create(MaintenanceTask task) {
        if (task.getVehicleRegistration() != null) {
            Vehicle v = vehicleRepo.findById(task.getVehicleRegistration())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));
            task.setVehicle(v);
        }
        return repository.save(task);
    }

    public List<MaintenanceTask> findAll() {
        return repository.findAll(); }

   /*
    public MaintenanceTask save(MaintenanceTask task) {
        return repository.save(task);
    }
    */

}