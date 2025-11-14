#!/bin/bash

echo "==================================="
echo "DIAGNOSTIC FRONT-WEB"
echo "==================================="
echo ""

cd /home/user/Documents/Développement/Projets/Location_voitures/service-front-web

echo "1. Vérification Java..."
java -version 2>&1 | head -3
echo ""

echo "2. Vérification Maven..."
./mvnw --version 2>&1 | head -5
echo ""

echo "3. Compilation du projet..."
./mvnw clean compile -q
if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie"
else
    echo "❌ Erreur de compilation"
    exit 1
fi
echo ""

echo "4. Packaging du projet..."
./mvnw package -DskipTests -q
if [ $? -eq 0 ]; then
    echo "✅ Packaging réussi"
else
    echo "❌ Erreur de packaging"
    exit 1
fi
echo ""

echo "5. Vérification du JAR..."
if [ -f target/front-web-0.0.1-SNAPSHOT.jar ]; then
    echo "✅ JAR trouvé: $(ls -lh target/front-web-0.0.1-SNAPSHOT.jar | awk '{print $5}')"
else
    echo "❌ JAR non trouvé"
    exit 1
fi
echo ""

echo "6. Test du port 8087..."
if lsof -i :8087 >/dev/null 2>&1; then
    echo "⚠️  Port 8087 déjà utilisé"
    lsof -i :8087
else
    echo "✅ Port 8087 libre"
fi
echo ""

echo "7. Lancement de l'application..."
echo "   URL: http://localhost:8087/vehicules"
echo "   Appuyez sur Ctrl+C pour arrêter"
echo ""

java -jar target/front-web-0.0.1-SNAPSHOT.jar

