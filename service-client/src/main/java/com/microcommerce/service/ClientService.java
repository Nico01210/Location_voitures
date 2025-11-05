package com.microcommerce.service;

import com.microcommerce.model.Client;
import com.microcommerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Création de compte
    public Client creerCompte(Client client) {
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        return clientRepository.save(client);
    }

    // Authentification
    public boolean authentifier(String email, String motDePasse) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        if (clientOpt.isEmpty()) return false;
        return passwordEncoder.matches(motDePasse, clientOpt.get().getMotDePasse());
    }

    // Consultation du profil
    public Optional<Client> consulterProfil(String email) {
        return clientRepository.findByEmail(email);
    }
}
