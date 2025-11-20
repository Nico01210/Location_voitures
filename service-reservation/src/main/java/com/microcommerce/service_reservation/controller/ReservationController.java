package com.microcommerce.service_reservation.controller;

import com.microcommerce.service_reservation.dto.ClientDTO;
import com.microcommerce.service_reservation.dto.ReservationDTO;
import com.microcommerce.service_reservation.dto.VehiculeDTO;
import com.microcommerce.service_reservation.model.Reservation;
import com.microcommerce.service_reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     *   "nom": "Dupont",
     *   "prenom": "Jean",
     *   "dateNaissance": "1990-05-15",
     *   "numeroPermis": "ABC123456789AB",
     *   "anneePermis": 2015,
     *   "vehiculeId": 1,
     *   "dateDebut": "2025-02-01",
     *   "dateFin": "2025-02-05"
     * }
     */
    @PostMapping
    public ResponseEntity<?> creer(@RequestBody ReservationDTO reservationDTO) {
        try {
           ClientDTO clientVerification = service.apiGetClientWithId(reservationDTO.getClientId());

            ClientDTO clientReservation = new ClientDTO();
            clientReservation.setId(clientVerification.getId());
            clientReservation.setNom(clientVerification.getNom());
            clientReservation.setPrenom(clientVerification.getPrenom());
            clientReservation.setAge(clientVerification.getAge());
            clientReservation.setEmail(clientVerification.getEmail());
            clientReservation.setMotDePasse(clientVerification.getMotDePasse());
            clientReservation.setNumeroPermis(clientVerification.getNumeroPermis());
            clientReservation.setAnneePermis(clientVerification.getAnneePermis());

            VehiculeDTO vehiculeVerification = service.apiGetVehiculeWithId(reservationDTO.getVehiculeId());

            Reservation reservation = service.creerReservation(
                    clientReservation,
                    vehiculeVerification,
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
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            var reservation = service.getById(id);
            if (reservation.isPresent()) {
                return ResponseEntity.ok(reservation.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Erreur : Réservation non trouvée avec l'ID : " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur serveur : " + e.getMessage());
        }
    }
}


