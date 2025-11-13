package com.microcommerce.service_vehicules.controller;

import com.microcommerce.service_vehicules.model.MaintenanceTask;
import com.microcommerce.service_vehicules.model.Vehicle;
import com.microcommerce.service_vehicules.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaintenanceController {

    private final MaintenanceService service;

    @GetMapping("/{registration}")
    public List<MaintenanceTask> getTasks(@PathVariable String registration) {
        return service.getTasksByVehicle(registration);
    }

    @PostMapping
    public MaintenanceTask addTask(@RequestBody MaintenanceTask task) {
        return service.create(task);
    }

    @GetMapping ("/registrations")
    public List<MaintenanceTask> getAllVehicles() {
        return service.findAll();
    }


}