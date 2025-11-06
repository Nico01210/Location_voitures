#!/bin/bash

echo "🧹 Nettoyage..."
pkill -9 java 2>/dev/null
sleep 3

cd /home/user/Documents/Développement/Projets/Location_voitures/service-client

echo "🚀 Démarrage de l'application..."
mvn spring-boot:run > /tmp/spring-boot.log 2>&1 &
APP_PID=$!

echo "⏳ Attente du démarrage (20 secondes)..."
sleep 20

# Vérifier si l'application est démarrée
if curl -s http://localhost:8081/clients > /dev/null 2>&1; then
    echo "✅ Application démarrée !"
    echo ""

    # Ajouter des clients de test
    echo "👤 Ajout de clients de test..."

    curl -X POST http://localhost:8081/clients/register \
      -H "Content-Type: application/json" \
      -d '{"nom":"Dupont","prenom":"Jean","age":30,"email":"jean@example.com","motDePasse":"pass123"}' 2>/dev/null

    curl -X POST http://localhost:8081/clients/register \
      -H "Content-Type: application/json" \
      -d '{"nom":"Martin","prenom":"Marie","age":25,"email":"marie@example.com","motDePasse":"pass456"}' 2>/dev/null

    curl -X POST http://localhost:8081/clients/register \
      -H "Content-Type: application/json" \
      -d '{"nom":"Durand","prenom":"Paul","age":35,"email":"paul@example.com","motDePasse":"pass789"}' 2>/dev/null

    echo ""
    echo "✅ 3 clients ajoutés !"
    echo ""
    echo "📋 Liste des clients :"
    curl -s http://localhost:8081/clients | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8081/clients
    echo ""
    echo ""
    echo "🌐 Ouvrez dans votre navigateur :"
    echo "   http://localhost:8081/clients"
    echo ""
    echo "🗄️ Console H2 :"
    echo "   http://localhost:8081/h2-console"
    echo ""
    echo "⚠️  Ne fermez pas ce terminal !"
    echo ""

    # Garder l'application en cours
    wait $APP_PID
else
    echo "❌ Erreur de démarrage. Logs :"
    tail -50 /tmp/spring-boot.log
    kill $APP_PID 2>/dev/null
fi

