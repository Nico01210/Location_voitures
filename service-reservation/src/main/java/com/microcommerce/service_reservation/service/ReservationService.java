package com.microcommerce.service_reservation.service;

import com.microcommerce.service_reservation.dto.VehiculeDTO;
import com.microcommerce.service_reservation.model.Reservation;
import com.microcommerce.service_reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final RestTemplate restTemplate;

    public ReservationService(ReservationRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Reservation creerReservation(Reservation reservation) {
        // Simule temporairement un véhicule
        VehiculeDTO vehicule = getVehiculeById(reservation.getVehiculeId());

        if (!vehicule.isDisponible()) {
            throw new IllegalArgumentException("Le véhicule n'est pas disponible.");
        }

        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        reservation.setPrixTotal(vehicule.getPrixJournalier() * jours);

        return repository.save(reservation);
    }

    public List<Reservation> listerToutes() {
        return repository.findAll();
    }

    public Optional<Reservation> getById(Long id) {
        return repository.findById(id);
    }

    // 🔌 Simulation temporaire (à remplacer par appel réel plus tard)
    private VehiculeDTO getVehiculeById(Long id) {
        return new VehiculeDTO(id, "Peugeot", "208", 45.0, true);
        // FUTUR : return restTemplate.getForObject("http://SERVICE-VEHICULES/vehicules/" + id, VehiculeDTO.class);
    }
}
