package com.microcommerce.service_vehicules.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("Motorcycle")
public class Motorcycle extends Vehicle {
    private int cylinder;

}