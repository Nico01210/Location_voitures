package com.microcommerce.service_vehicules.service;

import com.microcommerce.service_vehicules.model.Car;
import com.microcommerce.service_vehicules.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;

    public List<Car> findAll() {
        return repository.findAll();
    }

    public Car findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car save(Car car) {
        return repository.save(car);
    }
}