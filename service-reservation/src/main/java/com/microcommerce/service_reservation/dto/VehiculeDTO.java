package com.microcommerce.service_reservation.dto;

public class VehiculeDTO {
    private Long id;
    private String marque;
    private String modele;
    private Double prixJournalier;
    private boolean disponible;

    public VehiculeDTO() {}

    public VehiculeDTO(Long id, String marque, String modele, Double prixJournalier, boolean disponible) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.prixJournalier = prixJournalier;
        this.disponible = disponible;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }
    public Double getPrixJournalier() { return prixJournalier; }
    public void setPrixJournalier(Double prixJournalier) { this.prixJournalier = prixJournalier; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
