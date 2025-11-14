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
        createVehicule("CAR-001", "Renault", "Clio", "Rouge", 50.0, 0.25, 6, "https://via.placeholder.com/300x200?text=Toyota+Corolla"),
        createVehicule("CAR-002", "Peugeot", "208","Bleu", 55.0, 0.30, 7, "https://via.placeholder.com/300x200?text=Peugeot+208"),
        createVehicule("MOTO-001", "Yamaha", "MT-07","Noir", 40.0, 0.20, 5, "https://via.placeholder.com/300x200?text=Yamaha+MT-07"),
        createVehicule("MOTO-002", "Honda", "CB500", "Blanc",38.0, 0.18, 4, "https://via.placeholder.com/300x200?text=Honda+CB500"),
        createVehicule("UTIL-001", "Renault", "Master", "Blanc", 75.0, 0.45, 10, "https://via.placeholder.com/300x200?text=Renault+Master")
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
     * Récupère un véhicule fictif par immatriculation
     * GET /api/test/vehicules/{registration}
     */
    @GetMapping("/{registration}")
    public ResponseEntity<VehiculeDTO> getVehiculeByRegistration(@PathVariable String registration) {
        Optional<VehiculeDTO> vehicule = TEST_VEHICULES.stream()
                .filter(v -> v.getRegistration().equals(registration))
                .findFirst();

        return vehicule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée un VehiculeDTO fictif
     */
    private static VehiculeDTO createVehicule(
            String registration,
            String brand,
            String model,
            String color,
            Double basePrice,
            Double pricePerKm,
            Integer fiscalPower,
            String photoUrl
    ) {
        VehiculeDTO v = new VehiculeDTO();
        v.setRegistration(registration);
        v.setBrand(brand);
        v.setModel(model);
        v.setColor(color);
        v.setBasePrice(basePrice);
        v.setPricePerKm(pricePerKm);
        v.setFiscalPower(fiscalPower);
        v.setPhotoUrl(photoUrl);
        return v;
    }
}

