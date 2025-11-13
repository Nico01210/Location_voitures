package com.microcommerce.service_vehicules.repository;

import com.microcommerce.service_vehicules.model.MaintenanceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
    List<MaintenanceTask> findByVehicle_Registration(String registration);
}