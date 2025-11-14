package com.locationvoiture.front.front_web.dto;

import java.time.LocalDate;

public class ReservationForm {

    private Long vehiculeId;
    private Long clientId;      // simple saisie côté front
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public ReservationForm() {}

    public ReservationForm(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    // ===== GETTERS =====
    public Long getVehiculeId() { return vehiculeId; }
    public Long getClientId() { return clientId; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }

    // ===== SETTERS =====
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}
