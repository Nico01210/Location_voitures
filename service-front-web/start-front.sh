#!/bin/bash

echo "🚀 Démarrage de Front-Web..."
echo ""

cd /home/user/Documents/Développement/Projets/Location_voitures/service-front-web

# Compilation
echo "📦 Compilation..."
./mvnw clean package -DskipTests -q

if [ ! -f target/front-web-0.0.1-SNAPSHOT.jar ]; then
    echo "❌ JAR non trouvé!"
    exit 1
fi

echo "✅ JAR créé"
echo ""
echo "🌐 Accédez à: http://localhost:8087"
echo "⚠️  Assurez-vous que service-vehicules tourne sur le port 8082"
echo ""

java -jar target/front-web-0.0.1-SNAPSHOT.jar

