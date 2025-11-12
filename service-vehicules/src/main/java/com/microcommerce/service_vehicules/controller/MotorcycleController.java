package com.microcommerce.service_vehicules.controller;

import com.microcommerce.service_vehicules.model.Motorcycle;
import com.microcommerce.service_vehicules.service.MotorcycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MotorcycleController {
    private final MotorcycleService service;

    @GetMapping
    public List<Motorcycle> getAllMotorcycles() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Motorcycle getMotorcycle(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public Motorcycle createMotorcycle(@RequestBody Motorcycle motorcycle) {
        return service.save(motorcycle);
    }
}