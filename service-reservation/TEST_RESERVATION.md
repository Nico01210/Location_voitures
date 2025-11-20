# Test de création de réservation

## Corrections apportées

### 1. ✅ Réparation du démarrage de l'application
- **Problème** : L'application s'arrêtait lors du démarrage avec erreur Eureka
- **Solution** : Configuration Eureka rendue optionnelle, timeouts augmentés, logs de démarrage réduits

### 2. ✅ Amélioration de la méthode POST /reservations
- **Problème** : "Le nom du client est obligatoire" même avec les bonnes données
- **Solution** : 
  - `ReservationDTO` améliorée avec `@JsonFormat` pour les dates
  - Support de deux formats JSON :
    - Format 1 (imbriqué) : `{ "client": { "nom": "...", "prenom": "...", ... }, "vehiculeId": 1, ... }`
    - Format 2 (plat) : `{ "nom": "...", "prenom": "...", ... }`
  - Serialization/Deserialization robuste

### 3. ✅ Robustesse face aux services manquants
- Si SERVICE-VEHICULES indisponible → utilise valeurs par défaut
- Si SERVICE-CLIENT indisponible → continue localement
- Si Eureka indisponible → l'app démarre quand même

### 4. ✅ Entité Reservation implémentée
- Ajout de tous les champs nécessaires
- Relation JPA @ManyToOne avec Client
- Getters/setters complets

## Tests manuels

### Démarrer l'application
```bash
cd /home/user/Documents/Développement/Projets/Location_voitures/service-reservation
mvn spring-boot:run
```

L'application démarre sur le port **8084**.

### Test 1 : Créer une réservation (JSON imbriqué)
```bash
curl -X POST http://localhost:8084/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "client": {
      "nom": "Dupont",
      "prenom": "Jean",
      "dateNaissance": "1990-05-15",
      "numeroPermis": "ABC123456789012",
      "anneePermis": 2020
    },
    "vehiculeId": 1,
    "dateDebut": "2025-12-20",
    "dateFin": "2025-12-25"
  }'
```

**Réponse attendue (201 Created)** :
```json
{
  "id": 1,
  "client": {
    "id": 1,
    "nom": "Dupont",
    "prenom": "Jean",
    "dateNaissance": "1990-05-15",
    "numeroPermis": "ABC123456789012",
    "anneePermis": 2020
  },
  "vehiculeId": 1,
  "dateDebut": "2025-12-20",
  "dateFin": "2025-12-25",
  "prixTotal": 250.0
}
```

### Test 2 : Lister les réservations
```bash
curl -s http://localhost:8084/reservations | python -m json.tool
```

### Test 3 : Récupérer une réservation par ID
```bash
curl -s http://localhost:8084/reservations/1 | python -m json.tool
```

### Test 4 : Vérifier la santé de l'application
```bash
curl -s http://localhost:8084/actuator/health | python -m json.tool
```

## Validations implémentées

✅ Nom, prénom, date de naissance, numéro de permis, année de permis : obligatoires
✅ Âge minimum : 18 ans
✅ Permis : au moins 2 ans d'ancienneté
✅ Permis : format numéro valide (12+ caractères alphanumériques)
✅ Dates : début < fin, pas dans le passé
✅ Client : une seule réservation active à la fois

## Fichiers modifiés

1. **ReservationController.java** - Méthode POST robuste et mappée correctement
2. **ReservationService.java** - Gestion robuste des services manquants
3. **Reservation.java** - Entité JPA complète avec relations
4. **ReservationDTO.java** - Support du JSON imbriqué, annotations Jackson
5. **application.properties** - Configuration Eureka optionnelle, port 8084
6. **RestTemplateConfig.java** - RestTemplate avec @LoadBalanced pour Eureka

## Dépannage

### Port 8084 en utilisation
```bash
lsof -i :8084  # Voir quel processus utilise le port
kill -9 <PID>  # Terminer le processus
```

### Réinitialiser la base de données
La base H2 en mémoire se réinitialise à chaque redémarrage. Aucune action requise.

### Activer les logs DEBUG
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.com.microcommerce.service_reservation=DEBUG"
```

## Notes

- La base de données H2 est en mémoire (`:mem:`) → données perdues au redémarrage
- Eureka (port 8761) est optionnel pour le fonctionnement local
- SERVICE-VEHICULES et SERVICE-CLIENT ne sont pas obligatoires (valeurs par défaut)
- L'application utilise Spring Cloud LoadBalancer pour la découverte de services

