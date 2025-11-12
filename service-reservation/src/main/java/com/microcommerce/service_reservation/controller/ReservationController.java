package com.microcommerce.service_reservation.controller;

import com.microcommerce.service_reservation.model.Reservation;
import com.microcommerce.service_reservation.service.ReservationService;
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

    @PostMapping
    public ResponseEntity<Reservation> creer(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(service.creerReservation(reservation));
    }

    @GetMapping
    public List<Reservation> lister() {
        return service.listerToutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
