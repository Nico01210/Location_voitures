# Configuration H2 Persistante - Guide d'utilisation

## ✅ Modifications effectuées

### 1. **Configuration persistante (application.properties)**
   - **Avant** : `ddl-auto=create-drop` (supprimait la base à chaque démarrage)
   - **Après** : `ddl-auto=update` (conserve les données)
   - **Chemin de la base** : `./data/vehicule_db` (créé automatiquement dans le répertoire du projet)

### 2. **Initialisation des véhicules (VehicleDataInitializer.java)**
   - Nouvelle classe créée pour initialiser les données de test
   - **Au premier démarrage** : crée 6 véhicules (2 voitures, 2 motos, 2 utilitaires)
   - **Aux démarrages suivants** : vérifie que les données existent et ne crée rien de nouveau
   - Les véhicules restent persistants entre les redémarrages

## 📊 Véhicules initialisés

### Voitures (Cars)
1. **Toyota Corolla** (AB-123-CD) - Noir - 50€/jour
2. **Renault Clio** (EF-456-GH) - Bleu - 40€/jour

### Motos (Motorcycles)
1. **Honda CB500** (IJ-789-KL) - Rouge - 30€/jour - 500cc
2. **Yamaha MT-07** (MN-012-OP) - Gris - 35€/jour - 689cc

### Utilitaires (Utilitaire)
1. **Ford Transit** (QR-345-ST) - Blanc - 60€/jour - 10m³
2. **Peugeot Boxer** (UV-678-WX) - Jaune - 55€/jour - 12m³

## 🚀 Utilisation

### Démarrer le service
```bash
./mvnw spring-boot:run
```

### Accéder à la console H2
- **URL** : http://localhost:8082/h2-console
- **JDBC URL** : `jdbc:h2:file:./data/vehicule_db`
- **Username** : `sa`
- **Password** : (laisser vide)

### Utiliser les véhicules dans votre frontend
- **API endpoints disponibles** :
  - `GET /api/vehicles` - Récupérer tous les véhicules
  - `GET /api/cars` - Récupérer toutes les voitures
  - `GET /api/motorcycles` - Récupérer toutes les motos
  - `GET /api/utilitaires` - Récupérer tous les utilitaires
  - `POST /api/vehicles` - Ajouter un nouveau véhicule
  - Et d'autres endpoints selon votre configuration

## 📁 Fichier de base de données

La base de données sera stockée à :
```
./data/vehicule_db.mv.db
./data/vehicule_db.trace.db
```

Ces fichiers persisteront tant que vous ne les supprimez pas.

## 🔄 Options de gestion des données

### Pour **réinitialiser la base de données** (supprimer toutes les données)
1. Supprimer le dossier `./data/`
2. Redémarrer l'application
3. Les données de test seront réinitialisées

### Pour **ajouter de nouveaux véhicules** en permanence
Utilisez l'API POST ou ajoutez-les dans `VehicleDataInitializer.java` avant le premier démarrage.

### Pour **modifier le schéma de la base de données**
Gardez `ddl-auto=update` pour que Hibernate applique les modifications sans perdre les données.

## 📝 Notes importantes

- ✅ Les données sont **persistantes** entre les redémarrages
- ✅ La base H2 est **fichier** (pas en mémoire)
- ✅ Pas d'installation externe nécessaire
- ✅ Parfait pour le développement et les tests
- ⚠️ Les tâches de maintenance seront créées automatiquement au démarrage

