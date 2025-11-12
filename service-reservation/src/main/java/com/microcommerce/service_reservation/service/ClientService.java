package com.microcommerce.service_reservation.service;

import com.microcommerce.service_reservation.model.Client;
import com.microcommerce.service_reservation.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    /**
     * Crée un nouveau client avec validation
     */
    public Client creerClient(Client client) {
        validerClient(client);
        return repository.save(client);
    }

    /**
     * Valide les données d'un client
     */
    public void validerClient(Client client) {
        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException("❌ Le nom est obligatoire.");
        }
        if (client.getPrenom() == null || client.getPrenom().isBlank()) {
            throw new IllegalArgumentException("❌ Le prénom est obligatoire.");
        }
        if (client.getDateNaissance() == null) {
            throw new IllegalArgumentException("❌ La date de naissance est obligatoire.");
        }

        int age = Period.between(client.getDateNaissance(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("❌ Le client doit avoir au moins 18 ans.");
        }

        if (client.getNumeroPermis() == null || client.getNumeroPermis().isBlank()) {
            throw new IllegalArgumentException("❌ Le numéro de permis est obligatoire.");
        }
        if (client.getAnneePermis() == null) {
            throw new IllegalArgumentException("❌ L'année d'obtention du permis est obligatoire.");
        }
    }

    /**
     * Récupère tous les clients
     */
    public List<Client> listerTous() {
        return repository.findAll();
    }

    /**
     * Récupère un client par ID
     */
    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    /**
     * Récupère un client par numéro de permis
     */
    public Optional<Client> getByNumeroPermis(String numeroPermis) {
        return Optional.ofNullable(repository.findByNumeroPermis(numeroPermis));
    }

    /**
     * Met à jour un client
     */
    public Client mettreAJour(Long id, Client clientMaj) {
        return repository.findById(id).map(client -> {
            if (clientMaj.getNom() != null) client.setNom(clientMaj.getNom());
            if (clientMaj.getPrenom() != null) client.setPrenom(clientMaj.getPrenom());
            if (clientMaj.getDateNaissance() != null) client.setDateNaissance(clientMaj.getDateNaissance());
            if (clientMaj.getNumeroPermis() != null) client.setNumeroPermis(clientMaj.getNumeroPermis());
            if (clientMaj.getAnneePermis() != null) client.setAnneePermis(clientMaj.getAnneePermis());
            return repository.save(client);
        }).orElseThrow(() -> new IllegalArgumentException("❌ Client non trouvé avec l'ID : " + id));
    }

    /**
     * Supprime un client
     */
    public void supprimer(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("❌ Client non trouvé avec l'ID : " + id);
        }
        repository.deleteById(id);
    }
}

