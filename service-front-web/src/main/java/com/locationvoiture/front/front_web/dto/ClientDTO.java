package com.locationvoiture.front.front_web.dto;

import java.time.LocalDate;

public class ClientDTO {

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numeroPermis;
    private Integer anneePermis;

    public ClientDTO() {}

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getNumeroPermis() { return numeroPermis; }
    public Integer getAnneePermis() { return anneePermis; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public void setNumeroPermis(String numeroPermis) { this.numeroPermis = numeroPermis; }
    public void setAnneePermis(Integer anneePermis) { this.anneePermis = anneePermis; }
}
