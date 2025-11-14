package com.locationvoiture.front.front_web.dto;

import java.time.LocalDate;

public class ReservationDTO {

    private Long id;            // ID généré côté backend
    private Long clientId;      // ID du client
    private Long vehiculeId;    // ID du véhicule réservé
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double prixTotal;   // calculé côté backend

    public ReservationDTO() {}

    public ReservationDTO(Long clientId, Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        this.clientId = clientId;
        this.vehiculeId = vehiculeId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // ===== GETTERS =====
    public Long getId() { return id; }
    public Long getClientId() { return clientId; }
    public Long getVehiculeId() { return vehiculeId; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public Double getPrixTotal() { return prixTotal; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setPrixTotal(Double prixTotal) { this.prixTotal = prixTotal; }

}
