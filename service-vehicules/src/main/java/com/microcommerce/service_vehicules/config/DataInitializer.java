package com.microcommerce.service_vehicules.config;

import com.microcommerce.service_vehicules.model.MaintenanceTask;
import com.microcommerce.service_vehicules.model.Vehicle;
import com.microcommerce.service_vehicules.repository.VehicleRepository;
import com.microcommerce.service_vehicules.repository.MaintenanceTaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final VehicleRepository vehicleRepo;
    private final MaintenanceTaskRepository maintenanceRepo;

    @PostConstruct
    public void initMaintenance() {
        List<Vehicle> vehicles = vehicleRepo.findAll();
        for (Vehicle v : vehicles) {
            if (maintenanceRepo.findByVehicle_Registration(v.getRegistration()).isEmpty()) {
                if (v.getClass().getSimpleName().equals("Motorcycle")) {
                    maintenanceRepo.save(new MaintenanceTask(null, "Tension de chaîne", 1000, 1, 1, LocalDate.now(), v, null));
                    maintenanceRepo.save(new MaintenanceTask(null, "Changement du liquide de frein", 0, 1, 1, LocalDate.now(), v, null));
                } else if (v.getClass().getSimpleName().equals("Car")) {
                    maintenanceRepo.save(new MaintenanceTask(null, "Changement de la courroie de distribution", 100000, 0, 3, LocalDate.now(), v, null));
                    maintenanceRepo.save(new MaintenanceTask(null, "Changement des pneus", 0, 1, 1, LocalDate.now(), v, null));
                }

            }
        }
    }
}