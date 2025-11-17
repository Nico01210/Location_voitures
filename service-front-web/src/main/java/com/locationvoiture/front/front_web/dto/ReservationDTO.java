package com.locationvoiture.front.front_web.dto;

import java.time.LocalDate;

public class ReservationDTO {

    private Long id;            // ID généré côté backend
    private Long clientId;      // ID du client
    private Long vehiculeId;
    private String registration;// Immatriculation du véhicule réservé
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double prixTotal;   // calculé côté backend

    // Informations client
    private String clientNom;
    private String clientPrenom;
    private LocalDate clientDateNaissance;
    private String clientNumeroPermis;
    private Integer clientAnneePermis;

    public ReservationDTO() {}

    public ReservationDTO(Long clientId, Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        this.clientId = clientId;
        this.registration = registration;
        this.vehiculeId = vehiculeId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // ===== GETTERS =====
    public String getRegistration() { return registration; }
    public Long getId() { return id; }
    public Long getClientId() { return clientId; }
    public Long getVehiculeId() { return vehiculeId; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public Double getPrixTotal() { return prixTotal; }

    public String getClientNom() { return clientNom; }
    public String getClientPrenom() { return clientPrenom; }
    public LocalDate getClientDateNaissance() { return clientDateNaissance; }
    public String getClientNumeroPermis() { return clientNumeroPermis; }
    public Integer getClientAnneePermis() { return clientAnneePermis; }

    // ===== SETTERS =====
    public void setRegistration(String registration) { this.registration = registration; }
    public void setId(Long id) { this.id = id; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setPrixTotal(Double prixTotal) { this.prixTotal = prixTotal; }

    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public void setClientPrenom(String clientPrenom) { this.clientPrenom = clientPrenom; }
    public void setClientDateNaissance(LocalDate clientDateNaissance) { this.clientDateNaissance = clientDateNaissance; }
    public void setClientNumeroPermis(String clientNumeroPermis) { this.clientNumeroPermis = clientNumeroPermis; }
    public void setClientAnneePermis(Integer clientAnneePermis) { this.clientAnneePermis = clientAnneePermis; }

}
