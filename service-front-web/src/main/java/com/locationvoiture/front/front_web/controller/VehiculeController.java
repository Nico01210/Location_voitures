package com.locationvoiture.front.front_web.controller;

import com.locationvoiture.front.front_web.dto.VehiculeDTO;
import com.locationvoiture.front.front_web.service.VehiculeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicules")
public class VehiculeController {

    private static final Logger logger = LoggerFactory.getLogger(VehiculeController.class);
    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    // Liste de tous les véhicules
    @GetMapping
    public String listVehicules(Model model) {
        try {
            List<VehiculeDTO> vehicules = vehiculeService.getAll();
            model.addAttribute("vehicules", vehicules);
            if (vehicules.isEmpty()) {
                model.addAttribute("message", "Aucun véhicule disponible pour le moment. Vérifiez que le microservice est en cours d'exécution.");
            }
            return "vehicules"; // templates/vehicules.html
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des véhicules", e);
            model.addAttribute("error", "Impossible de charger les véhicules: " + e.getMessage());
            return "error";
        }
    }

    // Détails d'un véhicule
    @GetMapping("/{id}")
    public String detailsVehicule(@PathVariable Long id, Model model) {
        try {
            VehiculeDTO vehicule = vehiculeService.getById(id);
            if (vehicule == null) {
                model.addAttribute("error", "Véhicule non trouvé (ID: " + id + ")");
                return "error";
            }
            model.addAttribute("vehicule", vehicule);
            return "vehicule-details"; // templates/vehicule-details.html
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du véhicule {}", id, e);
            model.addAttribute("error", "Impossible de charger le véhicule: " + e.getMessage());
            return "error";
        }
    }
}
