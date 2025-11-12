#!/bin/bash

echo "=========================================="
echo "🧹 NETTOYAGE ET DÉMARRAGE"
echo "=========================================="

# Tuer TOUS les processus Java
echo "1️⃣ Arrêt des processus Java..."
pkill -9 java 2>/dev/null
sleep 2

# Vérifier le port
echo "2️⃣ Vérification du port 8081..."
if lsof -i :8081 > /dev/null 2>&1; then
    echo "⚠️  Port 8081 occupé. Libération forcée..."
    lsof -i :8081 | awk 'NR>1 {print $2}' | xargs kill -9 2>/dev/null
    sleep 2
else
    echo "✅ Port 8081 libre"
fi

# Aller dans le dossier du projet
cd /home/user/Documents/Développement/Projets/Location_voitures/service-client

# Compiler si nécessaire
if [ ! -f "target/service-client-0.0.1-SNAPSHOT.jar" ]; then
    echo "3️⃣ Compilation du projet..."
    mvn clean package -DskipTests
fi

echo ""
echo "=========================================="
echo "🚀 DÉMARRAGE DE L'APPLICATION"
echo "=========================================="
echo ""

# Démarrer l'application avec Maven
mvn spring-boot:run

