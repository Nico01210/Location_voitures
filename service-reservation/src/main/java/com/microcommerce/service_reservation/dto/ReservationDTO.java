package com.microcommerce.service_reservation.dto;

import java.time.LocalDate;

public class ReservationDTO {
    private Long vehiculeId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    // Informations client
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numeroPermis;
    private Integer anneePermis;

    public ReservationDTO() {}

    // Getters et setters
    public Long getVehiculeId() { return vehiculeId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNumeroPermis() { return numeroPermis; }
    public void setNumeroPermis(String numeroPermis) { this.numeroPermis = numeroPermis; }

    public Integer getAnneePermis() { return anneePermis; }
    public void setAnneePermis(Integer anneePermis) { this.anneePermis = anneePermis; }
}

