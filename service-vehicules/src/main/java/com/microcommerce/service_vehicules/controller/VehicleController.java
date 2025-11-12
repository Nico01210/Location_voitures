package com.microcommerce.service_vehicules.controller;
import com.microcommerce.service_vehicules.model.Vehicle;
import com.microcommerce.service_vehicules.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService service; // GET /vehicles


    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle saved = service.save(vehicle);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getRegistration())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) {
        Vehicle existing = service.findById(id);
        existing.setBrand(vehicle.getBrand());
        existing.setModel(vehicle.getModel());
        existing.setColor(vehicle.getColor());
        existing.setBasePrice(vehicle.getBasePrice());
        existing.setPricePerKm(vehicle.getPricePerKm());
        existing.setFiscalPower(vehicle.getFiscalPower());
        return service.save(existing);
    }


}