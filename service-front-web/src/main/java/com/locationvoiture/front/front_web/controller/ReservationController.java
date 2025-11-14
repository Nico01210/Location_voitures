package com.locationvoiture.front.front_web.controller;

import com.locationvoiture.front.front_web.dto.ReservationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final WebClient reservationClient;

    public ReservationController(@Qualifier("reservationClient") WebClient reservationClient) {
        this.reservationClient = reservationClient;
    }

    // Formulaire pour créer une réservation
    @GetMapping("/new/{vehiculeId}")
    public String newReservationForm(@PathVariable Long vehiculeId, Model model) {
        ReservationForm form = new ReservationForm(vehiculeId);
        model.addAttribute("form", form);
        return "reservation-form"; // templates/reservation-form.html
    }

    // Création d'une réservation (POST)
    @PostMapping
    public String createReservation(@ModelAttribute("form") ReservationForm form, Model model) {
        try {
            logger.info("Création d'une réservation pour le véhicule: {}", form.getVehiculeId());

            reservationClient.post()
                    .uri("/reservations")
                    .bodyValue(form)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            logger.info("Réservation créée avec succès");
            return "confirmation"; // templates/confirmation.html

        } catch (WebClientRequestException e) {
            logger.error("Erreur de connexion au microservice de réservation: {}", e.getMessage());
            model.addAttribute("error", "Impossible de se connecter au service de réservation. Le service est probablement indisponible.");
            return "error";
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la réservation", e);
            model.addAttribute("error", "Une erreur est survenue lors de la création de la réservation: " + e.getMessage());
            return "error";
        }
    }
}

