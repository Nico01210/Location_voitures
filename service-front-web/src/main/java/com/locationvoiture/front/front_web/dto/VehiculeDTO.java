package com.locationvoiture.front.front_web.dto;

public class VehiculeDTO {

    private Long id;
    private String marque;
    private String modele;
    private Double prixParJour;
    private boolean disponible;
    private String photoUrl; // URL vers image du véhicule (optionnel)

    public VehiculeDTO() {}

    public VehiculeDTO(Long id, String marque, String modele, Double prixParJour, boolean disponible, String photoUrl) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.prixParJour = prixParJour;
        this.disponible = disponible;
        this.photoUrl = photoUrl;
    }

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public Double getPrixParJour() { return prixParJour; }
    public boolean isDisponible() { return disponible; }
    public String getPhotoUrl() { return photoUrl; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setModele(String modele) { this.modele = modele; }
    public void setPrixParJour(Double prixParJour) { this.prixParJour = prixParJour; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}
