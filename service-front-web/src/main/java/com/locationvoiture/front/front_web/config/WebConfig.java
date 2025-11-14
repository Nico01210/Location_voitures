package com.locationvoiture.front.front_web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    // WebClient pour le microservice Véhicule
    @Bean
    @Qualifier("vehiculeClient")
    public WebClient vehiculeClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082") // URL du microservice Véhicule
                .build();
    }

    // WebClient pour le microservice Réservation
    @Bean
    @Qualifier("reservationClient")
    public WebClient reservationClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083") // URL du microservice Réservation
                .build();
    }

    // (Optionnel) WebClient pour le microservice Client
    @Bean
    @Qualifier("clientClient")
    public WebClient clientClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081") // URL du microservice Client
                .build();
    }
}
