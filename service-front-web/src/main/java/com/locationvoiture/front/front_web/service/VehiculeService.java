package com.locationvoiture.front.front_web.service;

import com.locationvoiture.front.front_web.dto.VehiculeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class VehiculeService {

    private static final Logger logger = LoggerFactory.getLogger(VehiculeService.class);
    private final WebClient vehiculeClient;
    @Value("${app.use-test-data:false}")
    private boolean useTestData;


    public VehiculeService(@Qualifier("vehiculeClient") WebClient vehiculeClient) {
        this.vehiculeClient = vehiculeClient;
    }

    // Récupérer tous les véhicules
    public List<VehiculeDTO> getAll() {
        try {
            // Si le mode test est activé, utiliser les données de test
            if (useTestData) {
                logger.info("Mode TEST activé - utilisation des données fictives");
                return getTestVehicules();
            }

            VehiculeDTO[] response = vehiculeClient.get()
                    .uri("/vehicules")
                    .retrieve()
                    .bodyToMono(VehiculeDTO[].class)
                    .doOnError(error -> logger.error("Erreur lors de la récupération des véhicules: {}", error.getMessage()))
                    .onErrorReturn(new VehiculeDTO[0])
                    .block();
            return response != null ? Arrays.asList(response) : Collections.emptyList();
        } catch (Exception e) {
            logger.error("Exception lors de la récupération des véhicules", e);
            return Collections.emptyList();
        }
    }

    // Récupérer un véhicule par ID
    public VehiculeDTO getById(Long id) {
        try {
            // Si le mode test est activé, utiliser les données de test
            if (useTestData) {
                return getTestVehicules().stream()
                        .filter(v -> v.getId().equals(id))
                        .findFirst()
                        .orElse(null);
            }

            return vehiculeClient.get()
                    .uri("/vehicules/{id}", id)
                    .retrieve()
                    .bodyToMono(VehiculeDTO.class)
                    .doOnError(error -> logger.error("Erreur lors de la récupération du véhicule {}: {}", id, error.getMessage()))
                    .onErrorReturn(null)
                    .block();
        } catch (Exception e) {
            logger.error("Exception lors de la récupération du véhicule {}", id, e);
            return null;
        }
    }

    /**
     * Retourne les véhicules fictifs pour les tests
     */
    private List<VehiculeDTO> getTestVehicules() {
        VehiculeDTO v1 = new VehiculeDTO();
        v1.setId(1L);
        v1.setMarque("Toyota");
        v1.setModele("Corolla");
        v1.setPrixParJour(50.0);
        v1.setPhotoUrl("/images/Toyota_Corolla_Limousine_Hybrid_Genf_2019_1Y7A5576.jpg");
        v1.setDisponible(true);

        VehiculeDTO v2 = new VehiculeDTO();
        v2.setId(2L);
        v2.setMarque("BMW");
        v2.setModele("X5");
        v2.setPrixParJour(120.0);
        v2.setPhotoUrl("/images/bmw.jpeg");
        v2.setDisponible(true);

        VehiculeDTO v3 = new VehiculeDTO();
        v3.setId(3L);
        v3.setMarque("Mercedes");
        v3.setModele("C-Class");
        v3.setPrixParJour(90.0);
        v3.setPhotoUrl("/images/mercedes.jpg");
        v3.setDisponible(true);

        VehiculeDTO v4 = new VehiculeDTO();
        v4.setId(4L);
        v4.setMarque("Audi");
        v4.setModele("A4");
        v4.setPrixParJour(80.0);
        v4.setPhotoUrl("/images/audi.webp");
        v4.setDisponible(true);

        return Arrays.asList(v1, v2, v3, v4);
    }
}
