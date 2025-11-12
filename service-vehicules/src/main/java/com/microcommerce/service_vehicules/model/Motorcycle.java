package com.microcommerce.service_vehicules.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
public class Motorcycle extends Vehicle {
    private int cylinder;

}