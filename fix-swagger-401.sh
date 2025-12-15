#!/bin/bash

echo "=========================================="
echo "🔧 Fixing 401 Unauthorized for Swagger"
echo "=========================================="
echo ""

cd /Users/navinraj/Downloads/MultiRoleUserApp

# Kill existing process
echo "1️⃣  Killing existing processes..."
lsof -ti:8080 | xargs kill -9 2>/dev/null || true
sleep 2

# Clean build
echo "2️⃣  Cleaning project..."
./mvnw clean -q

# Build
echo "3️⃣  Building with updated security config..."
./mvnw package -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✓ Build successful!"
    echo ""
    echo "4️⃣  Starting application..."
    echo "=========================================="
    java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
else
    echo "❌ Build failed"
    exit 1
fi

