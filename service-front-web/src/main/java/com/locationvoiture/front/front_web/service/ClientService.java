package com.locationvoiture.front.front_web.service;

import com.locationvoiture.front.front_web.dto.ClientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final WebClient clientClient;

    public ClientService(@Qualifier("clientClient") WebClient clientClient) {
        this.clientClient = clientClient;
    }

    public ClientDTO getById(Long id) {
        try {
            logger.info("Récupération du client ID: {} depuis le service-client", id);

            return clientClient.get()
                    .uri("/clients/{id}", id)
                    .retrieve()
                    .bodyToMono(ClientDTO.class)
                    .doOnError(error -> logger.error("Erreur lors de la récupération du client {}: {}", id, error.getMessage()))
                    .onErrorReturn(null)
                    .block();
        } catch (Exception e) {
            logger.error("Exception lors de la récupération du client {}", id, e);
            return null;
        }
    }
}

