package com.microcommerce.controller;

import com.microcommerce.model.Client;
import com.microcommerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // 1️⃣ Créer un compte
    @PostMapping("/register")
    public ResponseEntity<Client> register(@RequestBody Client client) {
        Client savedClient = clientService.creerCompte(client);
        return ResponseEntity.ok(savedClient);
    }

    // 2️⃣ Authentification
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String motDePasse) {
        boolean ok = clientService.authentifier(email, motDePasse);
        if (ok) {
            return ResponseEntity.ok("Authentification réussie ✅");
        } else {
            return ResponseEntity.status(401).body("Identifiants invalides ❌");
        }
    }

    // 3️⃣ Consultation du profil
    @GetMapping("/profil/{email}")
    public ResponseEntity<Client> profil(@PathVariable String email) {
        Optional<Client> clientOpt = clientService.consulterProfil(email);
        return clientOpt
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Récupérer tous les clients
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
}
