#!/bin/bash

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     REDÉMARRAGE RAPIDE - SERVICE FRONT-WEB                 ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

cd "$(dirname "$0")"

# 1. Arrêt
echo -n "1️⃣  Arrêt des processus en cours... "
pkill -9 -f "front-web" 2>/dev/null
pkill -9 java 2>/dev/null
sleep 1
echo -e "${GREEN}✓${NC}"

# 2. Compilation
echo -n "2️⃣  Compilation du projet... "
if mvn clean compile -q 2>/dev/null; then
    echo -e "${GREEN}✓${NC}"
else
    echo -e "${RED}✗${NC}"
    echo ""
    echo "Erreur de compilation. Exécution avec détails :"
    mvn clean compile
    exit 1
fi

# 3. Lancement
echo "3️⃣  Lancement de l'application..."
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║  Application démarrée sur http://localhost:9091            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "Testez avec : http://localhost:9091/vehicules"
echo "Pour arrêter : Ctrl+C"
echo ""
echo "────────────────────────────────────────────────────────────"
echo ""

mvn spring-boot:run


