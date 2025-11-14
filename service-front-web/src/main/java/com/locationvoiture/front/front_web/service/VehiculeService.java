package com.locationvoiture.front.front_web.service;

import com.locationvoiture.front.front_web.dto.VehiculeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class VehiculeService {

    private static final Logger logger = LoggerFactory.getLogger(VehiculeService.class);
    private final WebClient vehiculeClient;

    public VehiculeService(@Qualifier("vehiculeClient") WebClient vehiculeClient) {
        this.vehiculeClient = vehiculeClient;
    }

    // Récupérer tous les véhicules depuis le service réel
    public List<VehiculeDTO> getAll() {
        try {
            logger.info("Récupération de tous les véhicules depuis le service-vehicule");

            VehiculeDTO[] response = vehiculeClient.get()
                    .uri("/api/vehicles")
                    .retrieve()
                    .bodyToMono(VehiculeDTO[].class)
                    .doOnError(error -> logger.error("Erreur lors de la récupération des véhicules: {}", error.getMessage()))
                    .onErrorReturn(new VehiculeDTO[0])
                    .block();

            List<VehiculeDTO> vehicules = response != null ? Arrays.asList(response) : Collections.emptyList();
            logger.info("Nombre de véhicules récupérés: {}", vehicules.size());
            return vehicules;
        } catch (Exception e) {
            logger.error("Exception lors de la récupération des véhicules", e);
            return Collections.emptyList();
        }
    }

    // Récupérer un véhicule par ID depuis le service réel
    public VehiculeDTO getById(Long id) {
        try {
            logger.info("Récupération du véhicule ID: {} depuis le service-vehicule", id);

            return vehiculeClient.get()
                    .uri("/api/vehicles/{id}", id)
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
}

