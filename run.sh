#!/bin/bash
# Script de démarrage simple

WORK_DIR="/home/user/Documents/Développement/Projets/Location_voitures"
cd "$WORK_DIR"

echo "🚀 Démarrage des services..."

# Tuer les anciens processus
pkill -f "java.*jar" 2>/dev/null || true
sleep 2

# Eureka Server
echo "1️⃣  Eureka Server..."
java -Xms256m -Xmx512m -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar > eureka.log 2>&1 &
sleep 10

# Service Vehicules
echo "2️⃣  Service Vehicules..."
java -Xms256m -Xmx512m -jar service-vehicules/target/service-vehicules-0.0.1-SNAPSHOT.jar > vehicules.log 2>&1 &
sleep 8

# Service Reservation
echo "3️⃣  Service Reservation..."
java -Xms256m -Xmx512m -jar service-reservation/target/service-reservation-0.0.1-SNAPSHOT.jar > reservation.log 2>&1 &
sleep 8

# Service Front Web
echo "4️⃣  Service Front Web..."
java -Xms256m -Xmx512m -jar service-front-web/target/front-web-0.0.1-SNAPSHOT.jar > front.log 2>&1 &
sleep 8

echo ""
echo "✅ Tous les services sont démarrés!"
echo ""
echo "📍 Accès:"
echo "   Eureka:      http://localhost:8761"
echo "   Front Web:   http://localhost:9091"
echo ""
echo "Pour arrêter: pkill -f 'java.*jar'"

