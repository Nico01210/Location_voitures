package com.microcommerce.service_vehicules.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public abstract class Vehicle {

    @Id
    private String registration;
    private String brand;
    private String model;
    private String color;
    private double basePrice;
    private double pricePerKm;
    private int fiscalPower;


    public String getRegistration() {
        return registration;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getColor() {
        return color;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public int getFiscalPower() {
        return fiscalPower;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public void setFiscalPower(int fiscalPower) {
        this.fiscalPower = fiscalPower;
    }

}