package com.microcommerce.service_vehicules.service;

import com.microcommerce.service_vehicules.model.Utilitaire;
import com.microcommerce.service_vehicules.repository.UtilitaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilitaireService {

    private final UtilitaireRepository repository;

    public List<Utilitaire> findAll() { return repository.findAll(); }

    public Utilitaire findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilitaire not found"));
    }

    public Utilitaire save(Utilitaire vehicle) {
        return repository.save(vehicle);
    }

    public Utilitaire update(String registration, Utilitaire data) {
        return repository.findById(registration)
                .map(existing -> {
                    existing.setBrand(data.getBrand());
                    existing.setModel(data.getModel());
                    existing.setColor(data.getColor());
                    existing.setBasePrice(data.getBasePrice());
                    existing.setPricePerKm(data.getPricePerKm());
                    existing.setFiscalPower(data.getFiscalPower());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Utilitaire not found"));
    }

}

