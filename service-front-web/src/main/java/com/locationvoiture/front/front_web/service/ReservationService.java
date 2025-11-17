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

    public void createReservation(ReservationForm form) {

        // Création d'un DTO compatible backend
        ReservationDTO dto = new ReservationDTO();
        dto.setVehiculeId(form.getVehiculeId());
        dto.setRegistration(form.getRegistration());
        dto.setDateDebut(form.getDateDebut());
        dto.setDateFin(form.getDateFin());

        // Construction du client à partir des infos du formulaire front
        dto.setClientNom(form.getClientNom());
        dto.setClientPrenom(form.getClientPrenom());
        dto.setClientDateNaissance(form.getClientDateNaissance());
        dto.setClientNumeroPermis(form.getClientNumeroPermis());
        dto.setClientAnneePermis(form.getClientAnneePermis());

        reservationClient.post()
                .uri("/reservations")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
