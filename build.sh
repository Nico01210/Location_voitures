#!/bin/bash
# Script de compilation de tous les services

WORK_DIR="/home/user/Documents/Développement/Projets/Location_voitures"
cd "$WORK_DIR"

echo "🔨 Compilation de tous les services..."
echo "======================================"

# Compiler Eureka
echo "1️⃣  Compilation eureka-server..."
cd "$WORK_DIR/eureka-server"
mvn clean package -DskipTests -q
if [ $? -eq 0 ]; then
    echo "   ✅ eureka-server compilé"
else
    echo "   ❌ Erreur lors de la compilation"
    exit 1
fi

# Compiler service-vehicules
echo "2️⃣  Compilation service-vehicules..."
cd "$WORK_DIR/service-vehicules"
mvn clean package -DskipTests -q
if [ $? -eq 0 ]; then
    echo "   ✅ service-vehicules compilé"
else
    echo "   ❌ Erreur lors de la compilation"
    exit 1
fi

# Compiler service-reservation
echo "3️⃣  Compilation service-reservation..."
cd "$WORK_DIR/service-reservation"
mvn clean package -DskipTests -q
if [ $? -eq 0 ]; then
    echo "   ✅ service-reservation compilé"
else
    echo "   ❌ Erreur lors de la compilation"
    exit 1
fi

# Compiler service-front-web
echo "4️⃣  Compilation service-front-web..."
cd "$WORK_DIR/service-front-web"
mvn clean package -DskipTests -q
if [ $? -eq 0 ]; then
    echo "   ✅ service-front-web compilé"
else
    echo "   ❌ Erreur lors de la compilation"
    exit 1
fi

echo ""
echo "======================================"
echo "✅ TOUS LES SERVICES SONT COMPILÉS"
echo "======================================"
echo ""
echo "🚀 Commande pour démarrer:"
echo "   cd $WORK_DIR && ./run.sh"

