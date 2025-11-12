package com.microcommerce.service_reservation.dto;

import java.time.LocalDate;

public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numeroPermis;
    private Integer anneePermis;

    public ClientDTO() {}

    public ClientDTO(String nom, String prenom, LocalDate dateNaissance, String numeroPermis, Integer anneePermis) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numeroPermis = numeroPermis;
        this.anneePermis = anneePermis;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

