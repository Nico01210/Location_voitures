package com.microcommerce.service_reservation.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    private Long vehiculeId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double prixTotal;

    public Reservation() {}

    public Reservation(Client client, Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        this.client = client;
        this.vehiculeId = vehiculeId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Long getClientId() { return client != null ? client.getId() : null; }
    public void setClientId(Long clientId) {
        if (this.client == null) this.client = new Client();
        this.client.setId(clientId);
    }

    public Long getVehiculeId() { return vehiculeId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public Double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(Double prixTotal) { this.prixTotal = prixTotal; }
}
