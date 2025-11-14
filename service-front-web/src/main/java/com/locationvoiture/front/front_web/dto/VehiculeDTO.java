package com.locationvoiture.front.front_web.dto;

public class VehiculeDTO {

    private String registration;
    private String brand;
    private String model;
    private String color;
    private String photoUrl;
    private Double basePrice;
    private Double pricePerKm;
    private Integer fiscalPower;

    // Champs optionnels selon le type de véhicule
    private Integer cylinder;      // Pour les motos
    private Integer volumeCargo;   // Pour les utilitaires

    public VehiculeDTO() {}

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(Double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public Integer getFiscalPower() {
        return fiscalPower;
    }

    public void setFiscalPower(Integer fiscalPower) {
        this.fiscalPower = fiscalPower;
    }

    public Integer getCylinder() {
        return cylinder;
    }

    public void setCylinder(Integer cylinder) {
        this.cylinder = cylinder;
    }

    public Integer getVolumeCargo() {
        return volumeCargo;
    }

    public void setVolumeCargo(Integer volumeCargo) {
        this.volumeCargo = volumeCargo;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
