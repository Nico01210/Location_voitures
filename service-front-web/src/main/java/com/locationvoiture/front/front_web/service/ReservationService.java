package com.locationvoiture.front.front_web.service;

import com.locationvoiture.front.front_web.dto.ReservationDTO;
import com.locationvoiture.front.front_web.dto.ReservationForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReservationService {

    private final WebClient reservationClient;

    public ReservationService(@Qualifier("reservationClient") WebClient reservationClient) {
        this.reservationClient = reservationClient;
    }

    // Créer une réservation à partir d'un formulaire
    public void createReservation(ReservationForm form) {
        // Conversion ReservationForm -> ReservationDTO
        ReservationDTO dto = new ReservationDTO();
        dto.setVehiculeId(form.getVehiculeId());
        dto.setClientId(form.getClientId());
        dto.setDateDebut(form.getDateDebut());
        dto.setDateFin(form.getDateFin());

        reservationClient.post()
                .uri("/reservations")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
