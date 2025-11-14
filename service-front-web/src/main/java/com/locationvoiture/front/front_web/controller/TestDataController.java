package com.locationvoiture.front.front_web.controller;

import com.locationvoiture.front.front_web.dto.VehiculeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test/vehicules")
public class TestDataController {

    // Véhicules fictifs pour les tests
    private static final List<VehiculeDTO> TEST_VEHICULES = Arrays.asList(
        createVehicule(1L, "Toyota", "Corolla", 50.0, "https://via.placeholder.com/300x200?text=Toyota+Corolla"),
        createVehicule(2L, "BMW", "X5", 120.0, "https://via.placeholder.com/300x200?text=BMW+X5"),
        createVehicule(3L, "Mercedes", "C-Class", 90.0, "https://via.placeholder.com/300x200?text=Mercedes+C-Class"),
        createVehicule(4L, "Audi", "A4", 80.0, "https://via.placeholder.com/300x200?text=Audi+A4")
    );

    /**
     * Récupère tous les véhicules fictifs
     * GET /api/test/vehicules
     */
    @GetMapping
    public ResponseEntity<VehiculeDTO[]> getAllVehicules() {
        return ResponseEntity.ok(TEST_VEHICULES.toArray(new VehiculeDTO[0]));
    }

    /**
     * Récupère un véhicule fictif par ID
     * GET /api/test/vehicules/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> getVehiculeById(@PathVariable Long id) {
        Optional<VehiculeDTO> vehicule = TEST_VEHICULES.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();

        return vehicule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée un VehiculeDTO fictif
     */
    private static VehiculeDTO createVehicule(Long id, String marque, String modele, double prixParJour, String photoUrl) {
        VehiculeDTO vehicule = new VehiculeDTO();
        vehicule.setId(id);
        vehicule.setMarque(marque);
        vehicule.setModele(modele);
        vehicule.setPrixParJour(prixParJour);
        vehicule.setPhotoUrl(photoUrl);
        vehicule.setDisponible(true);
        return vehicule;
    }
}

