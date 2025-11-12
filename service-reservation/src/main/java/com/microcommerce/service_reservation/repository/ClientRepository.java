package com.microcommerce.service_reservation.repository;

import com.microcommerce.service_reservation.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées si nécessaire
    Client findByNumeroPermis(String numeroPermis);
}

