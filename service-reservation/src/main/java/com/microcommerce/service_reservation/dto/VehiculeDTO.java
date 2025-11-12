package com.microcommerce.service_reservation.dto;

public class VehiculeDTO {
    private Long id;
    private String immatriculation;
    private String marque;
    private String modele;
    private String couleur;
    private Double prixJournalier;
    private Double tarifKilometrique;
    private Integer chevauxFiscaux;
    private Integer cylindree;  // Pour deux roues, en cm3
    private Double volume;      // Pour utilitaires, en m3
    private boolean disponible;
    private String type;        // "Voiture", "DeuxRoues", "Utilitaire"

    public VehiculeDTO() {}

    public VehiculeDTO(Long id, String marque, String modele, Double prixJournalier, boolean disponible) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.prixJournalier = prixJournalier;
        this.disponible = disponible;
    }

    public VehiculeDTO(Long id, String immatriculation, String marque, String modele, String couleur,
                       Double prixJournalier, Double tarifKilometrique, Integer chevauxFiscaux,
                       String type, boolean disponible) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.couleur = couleur;
        this.prixJournalier = prixJournalier;
        this.tarifKilometrique = tarifKilometrique;
        this.chevauxFiscaux = chevauxFiscaux;
        this.type = type;
        this.disponible = disponible;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }

    public Double getPrixJournalier() { return prixJournalier; }
    public void setPrixJournalier(Double prixJournalier) { this.prixJournalier = prixJournalier; }

    public Double getTarifKilometrique() { return tarifKilometrique; }
    public void setTarifKilometrique(Double tarifKilometrique) { this.tarifKilometrique = tarifKilometrique; }

    public Integer getChevauxFiscaux() { return chevauxFiscaux; }
    public void setChevauxFiscaux(Integer chevauxFiscaux) { this.chevauxFiscaux = chevauxFiscaux; }

    public Integer getCylindree() { return cylindree; }
    public void setCylindree(Integer cylindree) { this.cylindree = cylindree; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
