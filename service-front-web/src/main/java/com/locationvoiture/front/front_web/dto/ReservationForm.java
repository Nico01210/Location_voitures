package com.locationvoiture.front.front_web.dto;

import java.time.LocalDate;

public class ReservationForm {

    private Long vehiculeId;
    private String registration; // immatriculation du véhicule

    // Informations client
    private String clientNom;
    private String clientPrenom;
    private LocalDate clientDateNaissance;
    private String clientNumeroPermis;
    private Integer clientAnneePermis;

    // Dates de réservation
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public ReservationForm() {}

    public ReservationForm(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    // ===== GETTERS =====
    public Long getVehiculeId() { return vehiculeId; }
    public String getRegistration() { return registration; }

    public String getClientNom() { return clientNom; }
    public String getClientPrenom() { return clientPrenom; }
    public LocalDate getClientDateNaissance() { return clientDateNaissance; }
    public String getClientNumeroPermis() { return clientNumeroPermis; }
    public Integer getClientAnneePermis() { return clientAnneePermis; }

    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }

    // ===== SETTERS =====
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }
    public void setRegistration(String registration) { this.registration = registration; }

    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public void setClientPrenom(String clientPrenom) { this.clientPrenom = clientPrenom; }
    public void setClientDateNaissance(LocalDate clientDateNaissance) { this.clientDateNaissance = clientDateNaissance; }
    public void setClientNumeroPermis(String clientNumeroPermis) { this.clientNumeroPermis = clientNumeroPermis; }
    public void setClientAnneePermis(Integer clientAnneePermis) { this.clientAnneePermis = clientAnneePermis; }

    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}
