package com.microcommerce.service_reservation.service;

import com.microcommerce.service_reservation.dto.ClientDTO;
import com.microcommerce.service_reservation.dto.VehiculeDTO;
import com.microcommerce.service_reservation.model.Reservation;
import com.microcommerce.service_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    // URLs des services externes (via Eureka)
    private static final String SERVICE_VEHICULES_URL = "http://SERVICE-VEHICULE/api/vehicles";
    private static final String SERVICE_CLIENT_URL = "http://SERVICE-CLIENT/api/clients";

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Crée une nouvelle réservation avec validation complète du client et du véhicule
     * Le client est validé auprès du SERVICE-CLIENT uniquement (pas de stockage local)
     */
    @Transactional
    public Reservation creerReservation(ClientDTO clientDTO, VehiculeDTO vehicule, LocalDate dateDebut, LocalDate dateFin) {
        // 1️⃣ VALIDATION DES DONNÉES CLIENT VIA SERVICE-CLIENT
        Long clientId = validerEtEnregistrerClient(clientDTO);

        // 2️⃣ VALIDATION DES DATES
        validerDates(dateDebut, dateFin);

        // 3️⃣ VÉRIFICATION QU'IL N'Y A PAS DE RÉSERVATION ACTIVE POUR CE CLIENT
        verifierReservationUnique(clientId, dateDebut, dateFin);

        // 5️⃣ VÉRIFICATION SUPPLÉMENTAIRE DE DISPONIBILITÉ
       /* if(vehicule.isDisponible()){
            throw new IllegalArgumentException("❌ Le véhicule n'est pas disponible.");
        }

        */

        // 6️⃣ VALIDATION RESTRICTIONS PAR ÂGE ET VÉHICULE
        validerRestrictionsAgeVehicule(clientDTO, vehicule);

        // 7️⃣ CALCUL DU PRIX TOTAL
        double prixTotal = calculerPrixTotal(vehicule, dateDebut, dateFin);

        // 8️⃣ CRÉATION DE LA RÉSERVATION
        Reservation reservation = new Reservation(clientId, vehicule.getId(), dateDebut, dateFin);
        reservation.setPrixTotal(prixTotal);

        return reservationRepository.save(reservation);
    }

    /**
     * Valide et enregistre le client auprès du SERVICE-CLIENT, retourne son ID
     */
    private Long validerEtEnregistrerClient(ClientDTO clientDTO) {
        // Validation locale des données obligatoires
        if (clientDTO.getNom() == null || clientDTO.getNom().isBlank()) {
            throw new IllegalArgumentException("❌ Le nom du client est obligatoire.");
        }
        if (clientDTO.getPrenom() == null || clientDTO.getPrenom().isBlank()) {
            throw new IllegalArgumentException("❌ Le prénom du client est obligatoire.");
        }
        if (clientDTO.getAge() == null) {
            throw new IllegalArgumentException("❌ L'age est obligatoire.");
        }
        if (clientDTO.getNumeroPermis() == null || clientDTO.getNumeroPermis().isBlank()) {
            throw new IllegalArgumentException("❌ Le numéro de permis est obligatoire.");
        }
        if (clientDTO.getAnneePermis() == null) {
            throw new IllegalArgumentException("❌ L'année d'obtention du permis est obligatoire.");
        }

        // Valider l'âge minimum (18 ans)
        if (clientDTO.getAge() < 18) {
            throw new IllegalArgumentException("❌ Le client doit avoir au moins 18 ans pour louer un véhicule.");
        }

        // Valider le permis
        validerPermis(clientDTO);
        /*

        // Envoyer au SERVICE-CLIENT pour enregistrement/validation
        try {
            ClientDTO clientEnregistre = restTemplate.postForObject(
                    SERVICE_CLIENT_URL,
                    clientDTO,
                    ClientDTO.class
            );
            if (clientEnregistre == null || clientEnregistre.getId() == null) {
                throw new IllegalArgumentException("❌ Erreur lors de l'enregistrement du client au SERVICE-CLIENT.");
            }
            return clientEnregistre.getId();
        } catch (RestClientException e) {
            throw new IllegalArgumentException("❌ Erreur de communication avec le SERVICE-CLIENT: " + e.getMessage());
        }

         */
        return clientDTO.getId();
    }

    /**
     * Validation complète du permis de conduire
     */
    private void validerPermis(ClientDTO client) {
        int anneeActuelle = LocalDate.now().getYear();

        // 1️⃣ Vérifier que l'année d'obtention du permis est cohérente avec l'âge
        int anneeNaissance =  anneeActuelle - client.getAge();
        int ageMinimumPermis = 18;
        int anneePlusAnciennePermisAutorisee = anneeNaissance + ageMinimumPermis;

        if (client.getAnneePermis() < anneePlusAnciennePermisAutorisee) {
            throw new IllegalArgumentException(
                "❌ Année d'obtention du permis incohérente. Le client ne pouvait pas obtenir son permis avant "
                + anneePlusAnciennePermisAutorisee + " (âge minimum : 18 ans)."
            );
        }

        // 2️⃣ Vérifier que le permis n'est pas dans le futur
        if (client.getAnneePermis() > anneeActuelle) {
            throw new IllegalArgumentException("❌ L'année d'obtention du permis ne peut pas être dans le futur.");
        }

        // 3️⃣ Vérifier que le permis a au moins 2 ans d'ancienneté
        int anciennetePermis = anneeActuelle - client.getAnneePermis();
        if (anciennetePermis < 2) {
            throw new IllegalArgumentException(
                "❌ Le permis doit dater d'au moins 2 ans pour louer un véhicule. Ancienneté actuelle : "
                + anciennetePermis + " an(s)."
            );
        }

        // 4️⃣ Vérifier le format du numéro de permis
        String numeroPermis = client.getNumeroPermis().replaceAll("\\s+", "").toUpperCase();
        if (numeroPermis.length() < 12) {
            throw new IllegalArgumentException(
                "❌ Le numéro de permis doit contenir au moins 12 caractères. Longueur actuelle : "
                + numeroPermis.length() + "."
            );
        }

        if (!numeroPermis.matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException(
                "❌ Le numéro de permis ne doit contenir que des lettres majuscules et des chiffres."
            );
        }

        // 5️⃣ Vérifier que le permis n'est pas trop ancien
        if (anciennetePermis > 70) {
            throw new IllegalArgumentException(
                "❌ L'ancienneté du permis semble incorrecte (" + anciennetePermis + " ans). Veuillez vérifier l'année d'obtention."
            );
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
     * Vérifie qu'un client n'a pas déjà une réservation active
     */
    private void verifierReservationUnique(Long clientId, LocalDate dateDebut, LocalDate dateFin) {
        List<Reservation> reservationsExistantes = reservationRepository.findAll();

        var reservationsConflictuelles = reservationsExistantes.stream()
            .filter(r -> r.getClientId().equals(clientId) &&
                    !dateDebut.isAfter(r.getDateFin()) &&
                    !dateFin.isBefore(r.getDateDebut()))
            .toList();

        if (!reservationsConflictuelles.isEmpty()) {
            Reservation existante = reservationsConflictuelles.get(0);
            throw new IllegalArgumentException(
                "❌ Le client a déjà une réservation active du " +
                existante.getDateDebut() + " au " + existante.getDateFin() +
                " (Véhicule ID: " + existante.getVehiculeId() + "). " +
                "Un client ne peut réserver qu'un véhicule à la fois."
            );
        }
    }

    /**
     * Validation des restrictions d'accès par âge et type de véhicule
     */
    private void validerRestrictionsAgeVehicule(ClientDTO client, VehiculeDTO vehicule) {
        int age = LocalDate.now().getYear() - client.getAge();

        if (vehicule.getFiscalPower() == null) {
            throw new IllegalArgumentException("❌ Les chevaux fiscaux du véhicule ne sont pas définis.");
        }

        // Restriction pour les moins de 21 ans
        if (age < 21 && vehicule.getFiscalPower() >= 8) {
            throw new IllegalArgumentException(
                    "❌ Les clients de moins de 21 ans ne peuvent pas louer un véhicule de " +
                    vehicule.getFiscalPower() + " chevaux fiscaux (minimum 8)."
            );
        }

        // Restriction pour les 21-25 ans
        if (age >= 21 && age <= 25 && vehicule.getFiscalPower() >= 13) {
            throw new IllegalArgumentException(
                    "❌ Les clients de 21 à 25 ans ne peuvent pas louer un véhicule de " +
                    vehicule.getFiscalPower() + " chevaux fiscaux (minimum 13)."
            );
        }

        /*/
        / Restrictions supplémentaires par type de véhicule

        if ("DeuxRoues".equals(vehicule.getType())) {
            if (age < 21) {
                throw new IllegalArgumentException("❌ Les clients de moins de 21 ans ne peuvent pas louer de deux roues.");
            }
            if (vehicule.getCylindre() == null || vehicule.getCylindre() > 500) {
                throw new IllegalArgumentException("❌ Cylindrée non conforme pour ce type de client.");
            }
        }

         */
    }



    /**
     * Calcul du prix total en fonction du type de véhicule
     */
    private double calculerPrixTotal(VehiculeDTO vehicule, LocalDate dateDebut, LocalDate dateFin) {
        long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
        if (jours <= 0) jours = 1;

        double prixJours = vehicule.getBasePrice() * jours;
        double prixKilometrique = 0;

        if (vehicule.getPricePerKm() != null) {
            int kilometrage = 100;
            prixKilometrique = vehicule.getPricePerKm() * kilometrage;
/*
            if ("DeuxRoues".equals(vehicule.getType()) && vehicule.getCylindre() != null) {
                prixKilometrique = vehicule.getTarifKilometrique() * kilometrage * (vehicule.getCylindre() * 0.001);
            } else if ("Utilitaire".equals(vehicule.getType()) && vehicule.getVolume() != null) {
                prixKilometrique = vehicule.getTarifKilometrique() * kilometrage * (vehicule.getVolume() * 0.05);
            } else {
                prixKilometrique = vehicule.getTarifKilometrique() * kilometrage;
            }

 */
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
     * Récupère les informations d'un véhicule depuis le SERVICE-VEHICULES
     */
    private VehiculeDTO getVehiculeById(Long id) {
        try {
            return restTemplate.getForObject(SERVICE_VEHICULES_URL + "/" + id, VehiculeDTO.class);
        } catch (RestClientException e) {
            throw new IllegalArgumentException("❌ Erreur lors de la récupération du véhicule (Service indisponible).");
        } catch (Exception e) {
            throw new IllegalArgumentException("❌ Véhicule non trouvé avec l'ID : " + id);
        }
    }



    public ClientDTO apiGetClientWithId(Long clientId) {
        try {
            ClientDTO isAvailable = restTemplate.getForObject("http://SERVICE-CLIENT/clients/" + clientId,
                    ClientDTO.class);
            if (isAvailable != null) {
                return isAvailable;
            } else{
                throw new IllegalArgumentException("❌ Le client n'existe pas.");
            }
        } catch (RestClientException e) {
            System.err.println("⚠️ Service-client indisponible pour vérification.");
        }
        return null;
    }
    public VehiculeDTO apiGetVehiculeWithId(Long vehiculeId) {
        try {
            VehiculeDTO isAvailable = restTemplate.getForObject("http://SERVICE-VEHICULE/api/vehicles/" + vehiculeId,
                    VehiculeDTO.class);
            if (isAvailable != null) {
                return isAvailable;
            } else{
                throw new IllegalArgumentException("❌ Le véhicule n'existe pas.");
            }
        } catch (RestClientException e) {
            System.err.println("⚠️ Service-vehicule indisponible pour vérification.");
        }
        return null;
    }
}

