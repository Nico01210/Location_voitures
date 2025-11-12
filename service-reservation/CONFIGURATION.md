# ⚙️ GUIDE DE CONFIGURATION ET PERSONNALISATION

## 🔧 Configuration de l'Application

### Fichier `application.properties`

```properties
# Serveur
server.port=8080
server.servlet.context-path=/api/v1

# Base de données H2 (par défaut)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.root=INFO
logging.level.com.microcommerce=DEBUG
```

---

## 🎛️ CONSTANTES PERSONNALISABLES

### Dans `ReservationService.java`

```java
// AGE MINIMUMS (à modifier selon vos règles)
private static final int AGE_MINIMUM = 18;           // Minimum légal
private static final int AGE_RESTRICTION_21 = 21;    // Restriction 1
private static final int AGE_RESTRICTION_25 = 25;    // Restriction 2

// PUISSANCE MAXIMALE (chevaux fiscaux)
private static final int CHEVAUX_MAX_JEUNE = 7;      // < 21 ans
private static final int CHEVAUX_MAX_INTERMEDIAIRE = 12;  // 21-25 ans
private static final int CHEVAUX_MAX_ADULTE = Integer.MAX_VALUE; // >= 26 ans

// CYLINDRÉE MAXIMALE (deux-roues en cm³)
private static final int CYLINDREE_MAX_JEUNE = 500;  // < 25 ans
private static final int CYLINDREE_MAX_ADULTE = Integer.MAX_VALUE; // >= 25 ans

// ANCIENNETE PERMIS
private static final int PERMIS_ANCIENNETE_MIN = 2;  // En années

// KILOMÉTRAGE PAR DÉFAUT
private static final int KILOMÉTRAGE_PAR_DÉFAUT = 100; // km
```

**Exemple de modification :**

```java
// Pour interdire les deux-roues avant 25 ans
private void validerRestrictionsAgeVehicule(Client client, VehiculeDTO vehicule) {
    // ...
    if ("DeuxRoues".equals(vehicule.getType())) {
        if (age < 25) {  // ← Changé de 21 à 25
            throw new IllegalArgumentException("❌ Les clients de moins de 25 ans ne peuvent pas louer de deux roues.");
        }
    }
}
```

---

## 📊 CONFIGURATIONS DE TARIFS

### Modèle Tarifaire (dans VehiculeDTO)

#### Voiture Standard
```json
{
  "immatriculation": "AB001CD",
  "marque": "Peugeot",
  "modele": "208",
  "prixJournalier": 45.0,      // 45€/jour
  "tarifKilometrique": 0.15,    // 0.15€/km
  "chevauxFiscaux": 8,
  "type": "Voiture"
}
```

#### SUV Premium
```json
{
  "immatriculation": "AB002CD",
  "marque": "BMW",
  "modele": "X5",
  "prixJournalier": 150.0,      // 150€/jour
  "tarifKilometrique": 0.25,    // 0.25€/km
  "chevauxFiscaux": 15,
  "type": "Voiture"
}
```

#### Deux-roues
```json
{
  "immatriculation": "AB003CD",
  "marque": "Honda",
  "modele": "CB500F",
  "prixJournalier": 30.0,       // 30€/jour
  "tarifKilometrique": 0.10,    // 0.10€/km
  "cylindree": 471,
  "chevauxFiscaux": 5,
  "type": "DeuxRoues"
}
```

#### Utilitaire
```json
{
  "immatriculation": "AB004CD",
  "marque": "Mercedes",
  "modele": "Sprinter",
  "prixJournalier": 80.0,       // 80€/jour
  "tarifKilometrique": 0.20,    // 0.20€/km
  "volume": 12.5,               // 12.5 m³
  "chevauxFiscaux": 7,
  "type": "Utilitaire"
}
```

---

## 💰 FORMULES DE CALCUL PERSONNALISÉES

### Ajouter des frais supplémentaires

Dans `ReservationService.calculerPrixTotal()` :

```java
private double calculerPrixTotal(VehiculeDTO vehicule, LocalDate dateDebut, LocalDate dateFin) {
    long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
    if (jours <= 0) jours = 1;

    double prixJours = vehicule.getPrixJournalier() * jours;
    
    // 🆕 Ajouter un surcoût pour weekend
    if (isWeekendRental(dateDebut, dateFin)) {
        prixJours *= 1.1;  // +10%
    }
    
    // 🆕 Ajouter un surcoût pour courte durée
    if (jours < 3) {
        prixJours *= 1.05;  // +5%
    }
    
    // Calcul kilométrique (existant)
    double prixKilometrique = calculerPrixKilometrique(vehicule, 100);
    
    return prixJours + prixKilometrique;
}

private boolean isWeekendRental(LocalDate debut, LocalDate fin) {
    return debut.getDayOfWeek().getValue() >= 5 || 
           fin.getDayOfWeek().getValue() >= 5;
}
```

---

## 🔐 AJOUTER DE NOUVELLES RESTRICTIONS

### Exemple : Restriction par zone géographique

```java
private void validerZoneGeographique(Client client, VehiculeDTO vehicule) {
    // Hypothèse : le véhicule a un attribut "region"
    if ("Île-de-France".equals(vehicule.getRegion()) && 
        client.getCodePostal().startsWith("75")) {
        throw new IllegalArgumentException("❌ Ce véhicule n'est pas disponible en zone urbaine.");
    }
}
```

### Exemple : Restriction par score crédit

```java
private void validerScoreCredit(Client client) {
    int score = client.getScoreCredit();
    if (score < 300) {
        throw new IllegalArgumentException("❌ Score crédit insuffisant (minimum 300).");
    }
}
```

### Exemple : Restriction par nombre sinistres

```java
private void validerHistoriqueSinistres(Client client, VehiculeDTO vehicule) {
    int sinistres = client.getNombreSinistres();
    if (sinistres >= 3 && vehicule.getChevauxFiscaux() > 8) {
        throw new IllegalArgumentException("❌ Trop de sinistres : accès restreint aux véhicules puissants.");
    }
}
```

---

## 📱 AJOUTER DE NOUVEAUX ENDPOINTS

### Pour lister les véhicules disponibles

```java
@RestController
@RequestMapping("/vehicules")
public class VehiculeController {
    
    @GetMapping("/disponibles")
    public List<VehiculeDTO> listerDisponibles() {
        // À adapter selon votre service de véhicules
        return vehiculeService.listerDisponibles();
    }
    
    @GetMapping("/par-type/{type}")
    public List<VehiculeDTO> listerParType(@PathVariable String type) {
        return vehiculeService.listerParType(type);
    }
}
```

### Pour rechercher des réservations

```java
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    
    @GetMapping("/client/{clientId}")
    public List<Reservation> listerParClient(@PathVariable Long clientId) {
        return reservationService.listerParClient(clientId);
    }
    
    @GetMapping("/vehicule/{vehiculeId}")
    public List<Reservation> listerParVehicule(@PathVariable Long vehiculeId) {
        return reservationService.listerParVehicule(vehiculeId);
    }
}
```

---

## 🌐 INTÉGRER UN VRAI SERVICE DE VÉHICULES

Actuellement, `getVehiculeById()` retourne un DTO simulé.

### Ajouter RestTemplate au service

```java
@Configuration
public class RestTemplateConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

### Modifier ReservationService

```java
@Service
public class ReservationService {
    
    private final RestTemplate restTemplate;
    private static final String VEHICULE_SERVICE_URL = 
        "http://SERVICE-VEHICULES:8081/api/vehicules";
    
    public ReservationService(
        ReservationRepository reservationRepository,
        ClientRepository clientRepository,
        RestTemplate restTemplate) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }
    
    private VehiculeDTO getVehiculeById(Long id) {
        try {
            return restTemplate.getForObject(
                VEHICULE_SERVICE_URL + "/" + id,
                VehiculeDTO.class
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("❌ Véhicule non trouvé.");
        }
    }
}
```

---

## 📲 AJOUTER DE LA VALIDATION ASYNCHRONE

### Valider les informations de manière asynchrone

```java
@Service
public class ClientValidationService {
    
    @Async
    public CompletableFuture<Boolean> validerNumeroPermisAsync(String numeroPermis) {
        // Appel à un service externe (API gouvernementale, etc.)
        return CompletableFuture.completedFuture(true);
    }
    
    @Async
    public CompletableFuture<Integer> obtenirScoreCreditAsync(String nom) {
        // Appel à un service de scoring
        return CompletableFuture.completedFuture(500);
    }
}
```

Utilisation dans le service :

```java
CompletableFuture<Boolean> validation = 
    clientValidationService.validerNumeroPermisAsync(client.getNumeroPermis());

validation.thenAccept(isValid -> {
    if (!isValid) {
        throw new IllegalArgumentException("❌ Permis invalide.");
    }
});
```

---

## 🔔 AJOUTER DES NOTIFICATIONS

### Email après réservation

```java
@Component
public class ReservationNotificationListener {
    
    @EventListener
    public void onReservationCreated(ReservationCreatedEvent event) {
        Reservation reservation = event.getReservation();
        
        String subject = "Réservation confirmée #" + reservation.getId();
        String body = "Votre réservation du " + 
            reservation.getDateDebut() + " au " + 
            reservation.getDateFin() + 
            " pour un montant de " + 
            reservation.getPrixTotal() + "€";
        
        emailService.send(reservation.getClientEmail(), subject, body);
    }
}
```

---

## 🧪 TESTS UNITAIRES

### Exemple de test de validation

```java
@SpringBootTest
public class ReservationServiceTests {
    
    @Autowired
    private ReservationService service;
    
    @Test
    public void testClientTropJeune() {
        Client client = new Client("Dupont", "Jean", 
            LocalDate.now().minusYears(15), "AB123456789", 2023);
        
        assertThrows(
            IllegalArgumentException.class,
            () -> service.creerReservation(client, 1L, 
                LocalDate.now(), LocalDate.now().plusDays(5)),
            "Le client doit avoir au moins 18 ans"
        );
    }
    
    @Test
    public void testPermisRecent() {
        Client client = new Client("Dupont", "Jean", 
            LocalDate.now().minusYears(25), "AB123456789", 2024);
        
        assertThrows(
            IllegalArgumentException.class,
            () -> service.creerReservation(client, 1L, 
                LocalDate.now(), LocalDate.now().plusDays(5)),
            "Le permis doit dater d'au moins 2 ans"
        );
    }
}
```

---

## 📊 MONITORING ET MÉTRIQUES

### Ajouter des logs détaillés

```java
@Service
@Slf4j
public class ReservationService {
    
    public Reservation creerReservation(Client client, Long vehiculeId, ...) {
        log.info("Création réservation - Client: {}, Véhicule: {}", 
            client.getId(), vehiculeId);
        
        try {
            validerClient(client);
            log.debug("Client valide: {}", client.getNom());
            // ... suite
        } catch (IllegalArgumentException e) {
            log.warn("Réservation rejetée: {}", e.getMessage());
            throw e;
        }
    }
}
```

### Ajouter des métriques Micrometer

```java
@Component
public class ReservationMetrics {
    
    private final MeterRegistry meterRegistry;
    
    public void recordReservation(Reservation reservation) {
        meterRegistry.timer("reservations.creation").record(() -> {
            // ...
        });
    }
}
```


