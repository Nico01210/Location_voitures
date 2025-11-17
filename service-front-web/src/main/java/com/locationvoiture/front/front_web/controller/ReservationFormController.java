package com.locationvoiture.front.front_web.controller;

import com.locationvoiture.front.front_web.dto.ReservationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservation-form")
public class ReservationFormController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationFormController.class);

    // Afficher le formulaire de réservation
    @GetMapping
    public String showReservationForm(@RequestParam(required = false) String registration, Model model) {
        logger.info("Affichage du formulaire de réservation pour: {}", registration);
        
        ReservationForm form = new ReservationForm();
        form.setRegistration(registration);
        model.addAttribute("form", form);
        model.addAttribute("registration", registration);
        
        return "reservation-form"; // templates/reservation-form.html
    }
}

