package com.microcommerce.service_reservation.dto;


public class VehiculeDTO {
    private Long id;
    private String registration;
    private String brand;
    private String model;
    private String color;
    private Double basePrice;
    private Double pricePerKm;
    private Integer fiscalPower;
    private String type;        // "Voiture", "DeuxRoues", "Utilitaire"

    public VehiculeDTO() {}

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
