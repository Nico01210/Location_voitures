# Exemples de Test avec cURL

## 🧪 Tester la Solution Complète

### 1️⃣ CRÉER UN CLIENT

**Requête (VALIDE)**
```bash
curl -X POST http://localhost:8080/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Bernard",
    "prenom": "Marie",
    "dateNaissance": "1995-03-15",
    "numeroPermis": "AB123456789",
    "anneePermis": 2020
  }'
```

**Réponse (201 Created)**
```json
{
  "id": 1,
  "nom": "Bernard",
  "prenom": "Marie",
  "dateNaissance": "1995-03-15",
  "numeroPermis": "AB123456789",
  "anneePermis": 2020
}
```

---

### 2️⃣ LISTER LES CLIENTS

```bash
curl http://localhost:8080/clients
```

---

### 3️⃣ CRÉER UNE RÉSERVATION

**Requête Complète (Avec validation)**
```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehiculeId": 1,
    "dateDebut": "2025-03-15",
    "dateFin": "2025-03-20",
    "nom": "Bernard",
    "prenom": "Marie",
    "dateNaissance": "1995-03-15",
    "numeroPermis": "AB123456789",
    "anneePermis": 2020
  }'
```

**Réponse (201 Created)**
```json
{
  "id": 1,
  "clientId": 1,
  "vehiculeId": 1,
  "dateDebut": "2025-03-15",
  "dateFin": "2025-03-20",
  "prixTotal": 225.75
}
```

---

## ❌ CAS D'ERREUR

### Erreur 1 : Client trop jeune

```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehiculeId": 1,
    "dateDebut": "2025-03-15",
    "dateFin": "2025-03-20",
    "nom": "Dupont",
    "prenom": "Jean",
    "dateNaissance": "2008-06-15",
    "numeroPermis": "AB987654321",
    "anneePermis": 2023
  }'
```

**Réponse (400 Bad Request)**
```json
{
  "error": "❌ Le client doit avoir au moins 18 ans pour louer un véhicule."
}
```

---

### Erreur 2 : Permis trop récent

```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehiculeId": 1,
    "dateDebut": "2025-03-15",
    "dateFin": "2025-03-20",
    "nom": "Martin",
    "prenom": "Sophie",
    "dateNaissance": "1998-06-15",
    "numeroPermis": "CD123456789",
    "anneePermis": 2024
  }'
```

**Réponse (400 Bad Request)**
```json
{
  "error": "❌ Le permis doit dater d'au moins 2 ans."
}
```

---

### Erreur 3 : Restriction puissance (Jeune + Voiture trop puissante)

```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehiculeId": 10,
    "dateDebut": "2025-03-15",
    "dateFin": "2025-03-20",
    "nom": "Olivier",
    "prenom": "Thomas",
    "dateNaissance": "2004-06-15",
    "numeroPermis": "EF456789123",
    "anneePermis": 2022
  }'
```

**Réponse (400 Bad Request)**
```json
{
  "error": "❌ Les clients de moins de 21 ans ne peuvent pas louer un véhicule de 15 chevaux fiscaux (minimum 8)."
}
```

---

### Erreur 4 : Deux-roues pour jeune conducteur

```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehiculeId": 20,
    "dateDebut": "2025-03-15",
    "dateFin": "2025-03-20",
    "nom": "Renaud",
    "prenom": "Lucas",
    "dateNaissance": "2005-06-15",
    "numeroPermis": "GH789456123",
    "anneePermis": 2023
  }'
```

**Réponse (400 Bad Request)**
```json
{
  "error": "❌ Les clients de moins de 21 ans ne peuvent pas louer de deux roues."
}
```

---

### Erreur 5 : Champ obligatoire manquant

```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "vehiculeId": 1,
    "dateDebut": "2025-03-15",
    "dateFin": "2025-03-20",
    "nom": "Soro",
    "prenom": "Alex",
    "dateNaissance": "1995-06-15"
  }'
```

**Réponse (400 Bad Request)**
```json
{
  "error": "❌ Le numéro de permis est obligatoire."
}
```

---

## 🧩 Exemple de Véhicule Simulé

Le système utilise actuellement des véhicules simulés. Voici la structure :

```java
// Peugeot 208 - Entrée de gamme
{
    id: 1,
    immatriculation: "AB123CD",
    marque: "Peugeot",
    modele: "208",
    couleur: "Blanche",
    prixJournalier: 45.0,
    tarifKilometrique: 0.15,
    chevauxFiscaux: 8,
    type: "Voiture",
    disponible: true
}

// Porsche 911 - Haute puissance
{
    id: 10,
    immatriculation: "AB999ZZ",
    marque: "Porsche",
    modele: "911",
    couleur: "Noire",
    prixJournalier: 350.0,
    tarifKilometrique: 0.50,
    chevauxFiscaux: 15,
    type: "Voiture",
    disponible: true
}

// Kawasaki Ninja - Deux-roues
{
    id: 20,
    immatriculation: "AB500MO",
    marque: "Kawasaki",
    modele: "Ninja 400",
    couleur: "Verte",
    prixJournalier: 30.0,
    tarifKilometrique: 0.10,
    chevauxFiscaux: 5,
    cylindree: 399,
    type: "DeuxRoues",
    disponible: true
}

// Mercedes Sprinter - Utilitaire
{
    id: 30,
    immatriculation: "AB666UT",
    marque: "Mercedes",
    modele: "Sprinter",
    couleur: "Blanche",
    prixJournalier: 80.0,
    tarifKilometrique: 0.20,
    chevauxFiscaux: 7,
    volume: 12.5,
    type: "Utilitaire",
    disponible: true
}
```

---

## 📊 Tableau de Calcul Prix

| Véhicule | Durée | Km | Calcul | Total |
|----------|-------|-----|--------|--------|
| Peugeot 208 | 5 jours | 500 km | (45 × 5) + (0.15 × 500) | 300 € |
| Kawasaki | 3 jours | 300 km | (30 × 3) + (0.10 × 300 × 0.399 × 0.001) | 90.01 € |
| Sprinter | 2 jours | 200 km | (80 × 2) + (0.20 × 200 × 12.5 × 0.05) | 165 € |
| Porsche 911 | 1 jour | 100 km | (350 × 1) + (0.50 × 100) | 400 € |


