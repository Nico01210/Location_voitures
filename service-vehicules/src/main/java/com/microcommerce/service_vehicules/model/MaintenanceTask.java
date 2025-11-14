package com.microcommerce.service_vehicules.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MaintenanceTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int intervalKm;
    private int intervalYears;
    private int downtimeDays;
    private LocalDate lastPerformed;


    //@JoinColumn(name = "vehicle_registration")
    @ManyToOne
    private Vehicle vehicle;


    @Transient
    private String vehicleRegistration;
}
