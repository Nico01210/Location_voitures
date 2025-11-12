package com.microcommerce.service_reservation.controller;

import com.microcommerce.service_reservation.model.Client;
import com.microcommerce.service_reservation.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    /**
     * Crée un nouveau client
     */
    @PostMapping
    public ResponseEntity<Client> creer(@RequestBody Client client) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.creerClient(client));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Liste tous les clients
     */
    @GetMapping
    public List<Client> lister() {
        return service.listerTous();
    }

    /**
     * Récupère un client par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupère un client par numéro de permis
     */
    @GetMapping("/permis/{numeroPermis}")
    public ResponseEntity<Client> getByNumeroPermis(@PathVariable String numeroPermis) {
        return service.getByNumeroPermis(numeroPermis)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Met à jour un client
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> mettreAJour(@PathVariable Long id, @RequestBody Client client) {
        try {
            return ResponseEntity.ok(service.mettreAJour(id, client));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprime un client
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        try {
            service.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

