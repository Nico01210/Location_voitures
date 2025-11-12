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
     * Format JSON:
     * {
     *   "client": {
     *     "nom": "Dupont",
     *     "prenom": "Jean",
     *     "dateNaissance": "1990-05-15",
     *     "numeroPermis": "ABC123456",
     *     "anneePermis": 2015
     *   },
     *   "vehiculeId": 1,
     *   "dateDebut": "2025-02-01",
     *   "dateFin": "2025-02-05"
     * }
     */
    @PostMapping
    public ResponseEntity<?> creer(@RequestBody ReservationDTO reservationDTO) {
        try {
            // Construire l'objet Client depuis les attributs de la DTO
            Client client = new Client(
                    reservationDTO.getNom(),
                    reservationDTO.getPrenom(),
                    reservationDTO.getDateNaissance(),
                    reservationDTO.getNumeroPermis(),
                    reservationDTO.getAnneePermis()
            );

            Reservation reservation = service.creerReservation(
                    client,
                    reservationDTO.getVehiculeId(),
                    reservationDTO.getDateDebut(),
                    reservationDTO.getDateFin()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur serveur : " + e.getMessage());
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


