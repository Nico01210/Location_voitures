package com.microcommerce.service_vehicules.controller;

import com.microcommerce.service_vehicules.model.Car;
import com.microcommerce.service_vehicules.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarController {
    private final CarService service;

    @GetMapping
    public List<Car> getAllCars() {
        return service.findAll();
    }


    @GetMapping("/{id}")
    public Car getCar(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) {
        Car saved = service.save(car);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getRegistration())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable String id, @RequestBody Car car) {
        Car existing = service.findById(id);
        existing.setBrand(car.getBrand());
        existing.setModel(car.getModel());
        existing.setColor(car.getColor());
        existing.setBasePrice(car.getBasePrice());
        existing.setPricePerKm(car.getPricePerKm());
        existing.setFiscalPower(car.getFiscalPower());
        return service.save(existing);
    }
}

