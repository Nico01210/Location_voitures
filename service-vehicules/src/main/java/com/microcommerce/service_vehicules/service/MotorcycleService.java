package com.microcommerce.service_vehicules.service;

import com.microcommerce.service_vehicules.model.Motorcycle;
import com.microcommerce.service_vehicules.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorcycleService {
    private final MotorcycleRepository repository;

    public List<Motorcycle> findAll() {
        return repository.findAll();
    }

    public Motorcycle findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Motorcycle not found"));
    }

    public Motorcycle save(Motorcycle motorcycle) {
        return repository.save(motorcycle);
    }
}
