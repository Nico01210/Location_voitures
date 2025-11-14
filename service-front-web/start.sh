#!/bin/bash

# Script de démarrage du front-web
echo "========================================="
echo "Démarrage du Front Web sur le port 8085"
echo "========================================="
echo ""

cd /home/user/Documents/Développement/Projets/Location_voitures/service-front-web

# Compiler et packager
echo "📦 Compilation et packaging..."
./mvnw clean package -q -DskipTests

# Vérifier si le build a réussi
if [ $? -eq 0 ]; then
    echo "✅ Build réussi!"
    echo ""
    echo "🚀 Démarrage de l'application..."
    echo "   Accédez à: http://localhost:8086"
    echo ""

    # Lancer le JAR
    java -jar target/front-web-0.0.1-SNAPSHOT.jar
else
    echo "❌ Erreur lors du build!"
    exit 1
fi

