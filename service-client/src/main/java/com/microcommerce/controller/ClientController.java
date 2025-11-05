package com.microcommerce.controller;

import com.microcommerce.model.Client;
import com.microcommerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

        import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Création de compte
    @PostMapping("/register")
    public Client register(@RequestBody Client client) {
        return clientService.creerCompte(client);
    }

    // Authentification
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String motDePasse) {
        boolean ok = clientService.authentifier(email, motDePasse);
        return ok ? "Authentification réussie ✅" : "Identifiants invalides ❌";
    }

    // Consultation du profil
    @GetMapping("/profil")
    public Optional<Client> profil(@RequestParam String email) {
        return clientService.consulterProfil(email);
    }
}
