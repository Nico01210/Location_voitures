#!/bin/bash

# Script de démarrage du service-reservation avec debug
echo "=========================================="
echo "🚀 Service Réservation - Démarrage DEBUG"
echo "=========================================="
echo ""

# 1. Tuer tous les processus Java sur le port 8083
echo "🔍 Recherche des processus sur le port 8083..."
PORT_PID=$(lsof -ti:8083 2>/dev/null)
if [ ! -z "$PORT_PID" ]; then
    echo "⚠️  Processus trouvé (PID: $PORT_PID). Arrêt..."
    kill -9 $PORT_PID 2>/dev/null
    sleep 2
    echo "✅ Processus arrêté"
else
    echo "✅ Port 8083 libre"
fi

# 2. Tuer tous les processus Maven du service
echo ""
echo "🧹 Nettoyage des processus Maven..."
pkill -9 -f "service-reservation" 2>/dev/null
sleep 1
echo "✅ Nettoyage terminé"

# 3. Vérifier Java et Maven
echo ""
echo "🔧 Vérification de l'environnement..."
java -version 2>&1 | head -1
mvn -version 2>&1 | head -1

# 4. Compiler le projet
echo ""
echo "📦 Compilation du projet..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation. Abandon."
    exit 1
fi
echo "✅ Compilation réussie"

# 5. Démarrer l'application
echo ""
echo "=========================================="
echo "🚀 DÉMARRAGE DE L'APPLICATION"
echo "=========================================="
echo "📌 Port: 8083"
echo "📌 H2 Console: http://localhost:8083/h2-console"
echo "📌 Swagger: http://localhost:8083/swagger-ui.html"
echo "📌 Eureka: http://localhost:8761/eureka/"
echo ""
echo "Logs en direct..."
echo "=========================================="
echo ""

mvn spring-boot:run

