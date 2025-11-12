package com.microcommerce.service_reservation.service;

import com.microcommerce.service_reservation.dto.VehiculeDTO;
import com.microcommerce.service_reservation.model.Client;
import com.microcommerce.service_reservation.model.Reservation;
import com.microcommerce.service_reservation.repository.ClientRepository;
import com.microcommerce.service_reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

    // URLs des services externes (via Eureka)
    private static final String SERVICE_VEHICULES_URL = "http://SERVICE-VEHICULES/api/vehicules";
    private static final String SERVICE_CLIENT_URL = "http://SERVICE-CLIENT/api/clients";

    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository, RestTemplate restTemplate) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Crée une nouvelle réservation avec validation complète du client et du véhicule
     * Communique avec SERVICE-CLIENT et SERVICE-VEHICULES
     */
    public Reservation creerReservation(Client client, Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        // 1️⃣ VALIDATION DU CLIENT (local)
        validerClient(client);

        // 2️⃣ VALIDATION AUPRÈS DU SERVICE-CLIENT
        validerClientAupresServiceClient(client);

        // 3️⃣ VALIDATION DES DATES (local)
        validerDates(dateDebut, dateFin);

        // 4️⃣ RÉCUPÉRATION ET VALIDATION DU VÉHICULE (service-vehicules)
        VehiculeDTO vehicule = getVehiculeById(vehiculeId);
        if (!vehicule.isDisponible()) {
            throw new IllegalArgumentException("❌ Le véhicule n'est pas disponible.");
        }

        // 5️⃣ VÉRIFICATION SUPPLÉMENTAIRE DE DISPONIBILITÉ
        verifierDisponibiliteVehicule(vehiculeId);

        // 6️⃣ VALIDATION RESTRICTIONS PAR ÂGE ET VÉHICULE (local)
        validerRestrictionsAgeVehicule(client, vehicule);

        // 7️⃣ SYNCHRONISATION DU CLIENT AVEC SERVICE-CLIENT
        Client clientSynchronise = synchroniserClientAvecServiceClient(client);

        // 8️⃣ SAUVEGARDE OU RÉCUPÉRATION DU CLIENT (local)
        Client clientFinal = sauvegarderOuRecupererClient(clientSynchronise);

        // 9️⃣ CALCUL DU PRIX TOTAL (local)
        double prixTotal = calculerPrixTotal(vehicule, dateDebut, dateFin);

        // 🔟 CRÉATION DE LA RÉSERVATION (local)
        Reservation reservation = new Reservation(clientFinal.getId(), vehiculeId, dateDebut, dateFin);
        reservation.setPrixTotal(prixTotal);

        return reservationRepository.save(reservation);
    }

    /**
     * Validation complète du client
     */
    private void validerClient(Client client) {
        // Vérifier les données obligatoires
        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException("❌ Le nom du client est obligatoire.");
        }
        if (client.getPrenom() == null || client.getPrenom().isBlank()) {
            throw new IllegalArgumentException("❌ Le prénom du client est obligatoire.");
        }
        if (client.getDateNaissance() == null) {
            throw new IllegalArgumentException("❌ La date de naissance est obligatoire.");
        }
        if (client.getNumeroPermis() == null || client.getNumeroPermis().isBlank()) {
            throw new IllegalArgumentException("❌ Le numéro de permis est obligatoire.");
        }
        if (client.getAnneePermis() == null) {
            throw new IllegalArgumentException("❌ L'année d'obtention du permis est obligatoire.");
        }

        // Vérifier l'âge minimum (18 ans)
        int age = Period.between(client.getDateNaissance(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("❌ Le client doit avoir au moins 18 ans pour louer un véhicule.");
        }

        // Vérifier que le permis n'est pas expiré (au moins 2 ans)
        int anciennetePermis = LocalDate.now().getYear() - client.getAnneePermis();
        if (anciennetePermis < 2) {
            throw new IllegalArgumentException("❌ Le permis doit dater d'au moins 2 ans.");
        }
    }

    /**
     * Validation des dates de réservation
     */
    private void validerDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("❌ Les dates de début et de fin sont obligatoires.");
        }
        if (dateDebut.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("❌ La date de début doit être dans le futur.");
        }
        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException("❌ La date de début doit être antérieure à la date de fin.");
        }
    }

    /**
     * Validation des restrictions d'accès par âge et type de véhicule
     */
    private void validerRestrictionsAgeVehicule(Client client, VehiculeDTO vehicule) {
        int age = Period.between(client.getDateNaissance(), LocalDate.now()).getYears();

        if (vehicule.getChevauxFiscaux() == null) {
            throw new IllegalArgumentException("❌ Les chevaux fiscaux du véhicule ne sont pas définis.");
        }

        // Restriction pour les moins de 21 ans
        if (age < 21 && vehicule.getChevauxFiscaux() >= 8) {
            throw new IllegalArgumentException(
                    "❌ Les clients de moins de 21 ans ne peuvent pas louer un véhicule de " +
                    vehicule.getChevauxFiscaux() + " chevaux fiscaux (minimum 8)."
            );
        }

        // Restriction pour les 21-25 ans
        if (age >= 21 && age <= 25 && vehicule.getChevauxFiscaux() >= 13) {
            throw new IllegalArgumentException(
                    "❌ Les clients de 21 à 25 ans ne peuvent pas louer un véhicule de " +
                    vehicule.getChevauxFiscaux() + " chevaux fiscaux (minimum 13)."
            );
        }

        // Restrictions supplémentaires par type de véhicule
        if ("DeuxRoues".equals(vehicule.getType())) {
            if (age < 21) {
                throw new IllegalArgumentException("❌ Les clients de moins de 21 ans ne peuvent pas louer de deux roues.");
            }
            if (vehicule.getCylindree() == null || vehicule.getCylindree() > 500) {
                throw new IllegalArgumentException("❌ Cylindrée non conforme pour ce type de client.");
            }
        }
    }

    /**
     * Sauvegarde ou récupère un client existant
     */
    private Client sauvegarderOuRecupererClient(Client client) {
        if (client.getId() != null) {
            // Client existant
            return clientRepository.findById(client.getId())
                    .orElseThrow(() -> new IllegalArgumentException("❌ Client non trouvé."));
        }

        // Vérifier s'il existe déjà par numéro de permis
        Client existant = clientRepository.findByNumeroPermis(client.getNumeroPermis());
        if (existant != null) {
            return existant;
        }

        // Créer un nouveau client
        return clientRepository.save(client);
    }

    /**
     * Calcul du prix total en fonction du type de véhicule
     */
    private double calculerPrixTotal(VehiculeDTO vehicule, LocalDate dateDebut, LocalDate dateFin) {
        long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
        if (jours <= 0) jours = 1;

        double prixJours = vehicule.getPrixJournalier() * jours;
        double prixKilometrique = 0;

        // Tarif kilométrique selon le type
        if (vehicule.getTarifKilometrique() != null) {
            int kilometrage = 100; // À adapter selon la réservation réelle

            if ("DeuxRoues".equals(vehicule.getType()) && vehicule.getCylindree() != null) {
                // Pour deux roues : tarif km x cylindrée x 0.001
                prixKilometrique = vehicule.getTarifKilometrique() * kilometrage * (vehicule.getCylindree() * 0.001);
            } else if ("Utilitaire".equals(vehicule.getType()) && vehicule.getVolume() != null) {
                // Pour utilitaires : tarif km x volume x 0.05
                prixKilometrique = vehicule.getTarifKilometrique() * kilometrage * (vehicule.getVolume() * 0.05);
            } else {
                // Pour voitures : tarif km standard
                prixKilometrique = vehicule.getTarifKilometrique() * kilometrage;
            }
        }

        return prixJours + prixKilometrique;
    }

    /**
     * Liste toutes les réservations
     */
    public List<Reservation> listerToutes() {
        return reservationRepository.findAll();
    }

    /**
     * Récupère une réservation par ID
     */
    public Optional<Reservation> getById(Long id) {
        return reservationRepository.findById(id);
    }

    /**
     * Récupère les informations d'un véhicule depuis le service-vehicules
     */
    private VehiculeDTO getVehiculeById(Long id) {
        try {
            String url = SERVICE_VEHICULES_URL + "/" + id;
            return restTemplate.getForObject(url, VehiculeDTO.class);
        } catch (RestClientException e) {
            throw new IllegalArgumentException("❌ Erreur lors de la récupération du véhicule (Service indisponible).");
        } catch (Exception e) {
            throw new IllegalArgumentException("❌ Véhicule non trouvé avec l'ID : " + id);
        }
    }

    /**
     * Crée ou met à jour un client auprès du service-client
     */
    private Client synchroniserClientAvecServiceClient(Client client) {
        try {
            String url = SERVICE_CLIENT_URL;
            // Envoyer le client au service-client pour synchronisation
            Client clientSynchronise = restTemplate.postForObject(url, client, Client.class);
            return clientSynchronise != null ? clientSynchronise : client;
        } catch (RestClientException e) {
            System.err.println("⚠️ Attention : Impossible de synchroniser avec le service-client : " + e.getMessage());
            // Continuer avec le client local si le service est indisponible
            return client;
        }
    }

    /**
     * Valide le client auprès du service-client
     */
    private void validerClientAupresServiceClient(Client client) {
        try {
            String url = SERVICE_CLIENT_URL + "/valider?numeroPermis=" + client.getNumeroPermis();
            Boolean isValid = restTemplate.getForObject(url, Boolean.class);
            if (isValid != null && !isValid) {
                throw new IllegalArgumentException("❌ Client rejeté par le service-client.");
            }
        } catch (RestClientException e) {
            System.err.println("⚠️ Service-client indisponible pour validation, continuant localement.");
            // Ne pas bloquer si le service-client est indisponible
        }
    }

    /**
     * Vérifie la disponibilité du véhicule auprès du service-vehicules
     */
    private void verifierDisponibiliteVehicule(Long vehiculeId) {
        try {
            String url = SERVICE_VEHICULES_URL + "/" + vehiculeId + "/disponible";
            Boolean isAvailable = restTemplate.getForObject(url, Boolean.class);
            if (isAvailable != null && !isAvailable) {
                throw new IllegalArgumentException("❌ Le véhicule n'est pas disponible.");
            }
        } catch (RestClientException e) {
            System.err.println("⚠️ Service-vehicules indisponible pour vérification.");
        }
    }
}
