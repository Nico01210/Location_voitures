package com.microcommerce.service_reservation.controller;

import com.microcommerce.service_reservation.dto.ReservationDTO;
import com.microcommerce.service_reservation.model.Client;
import com.microcommerce.service_reservation.model.Reservation;
import com.microcommerce.service_reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    /**
     * Crée une nouvelle réservation
     * Accepte un DTO contenant les informations du client et du véhicule
     */
    @PostMapping
    public ResponseEntity<?> creer(@RequestBody ReservationDTO dto) {
        try {
            // Construire l'objet Client depuis le DTO
            Client client = new Client(
                    dto.getNom(),
                    dto.getPrenom(),
                    dto.getDateNaissance(),
                    dto.getNumeroPermis(),
                    dto.getAnneePermis()
            );

            // Créer la réservation avec validations complètes
            Reservation reservation = service.creerReservation(
                    client,
                    dto.getVehiculeId(),
                    dto.getDateDebut(),
                    dto.getDateFin()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Erreur : " + e.getMessage());
        }
    }

    /**
     * Liste toutes les réservations
     */
    @GetMapping
    public List<Reservation> lister() {
        return service.listerToutes();
    }

    /**
     * Récupère une réservation par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


