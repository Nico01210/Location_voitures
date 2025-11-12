package com.microcommerce.service_vehicules.controller;

import com.microcommerce.service_vehicules.model.Motorcycle;
import com.microcommerce.service_vehicules.service.MotorcycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Motorcycle> createMotorcycle(@Valid @RequestBody Motorcycle motorcycle) {
        Motorcycle saved = service.save(motorcycle);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getRegistration())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{id}")
    public Motorcycle updateMotorcycle(@PathVariable String id, @RequestBody Motorcycle moto) {
        Motorcycle existing = service.findById(id);
        existing.setBrand(moto.getBrand());
        existing.setModel(moto.getModel());
        existing.setColor(moto.getColor());
        existing.setBasePrice(moto.getBasePrice());
        existing.setPricePerKm(moto.getPricePerKm());
        existing.setFiscalPower(moto.getFiscalPower());
        existing.setCylinder(moto.getCylinder());
        return service.save(existing);
    }
}