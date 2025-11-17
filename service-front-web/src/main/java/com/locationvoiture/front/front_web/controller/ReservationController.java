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

    // Formulaire par immatriculation
    @GetMapping
    public String reservationFormByRegistration(@RequestParam(required = false) String registration, Model model) {
        ReservationForm form = new ReservationForm();
        form.setRegistration(registration);
        model.addAttribute("form", form);
        model.addAttribute("registration", registration);
        return "reservation-form";
    }

    // Formulaire par ID
    @GetMapping("/new/{vehiculeId}")
    public String newReservationForm(@PathVariable Long vehiculeId, Model model) {
        ReservationForm form = new ReservationForm(vehiculeId);
        model.addAttribute("form", form);
        return "reservation-form";
    }

    // Création
    @PostMapping
    public String createReservation(@ModelAttribute("form") ReservationForm form, Model model) {
        try {
            logger.info("Création d'une réservation : vehiculeId={}, registration={}",
                    form.getVehiculeId(), form.getRegistration());

            reservationClient.post()
                    .uri("/reservations")
                    .bodyValue(form)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            return "confirmation";

        } catch (WebClientRequestException e) {
            logger.error("Erreur connexion service-résa : {}", e.getMessage());
            model.addAttribute("error", "Impossible de contacter le service de réservation.");
            return "error";

        } catch (Exception e) {
            logger.error("Erreur lors de la création : {}", e.getMessage());
            model.addAttribute("error", "Erreur lors de la création de la réservation : " + e.getMessage());
            return "error";
        }
    }
}
