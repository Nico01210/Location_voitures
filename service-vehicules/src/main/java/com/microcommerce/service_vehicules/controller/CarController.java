package com.microcommerce.service_vehicules.controller;

import com.microcommerce.service_vehicules.model.Car;
import com.microcommerce.service_vehicules.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public Car createCar(@RequestBody Car car) {
        return service.save(car);
    }
}

