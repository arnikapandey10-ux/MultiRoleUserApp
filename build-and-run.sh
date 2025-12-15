#!/bin/bash

echo "=========================================="
echo "Building Multi-Role Auth System"
echo "=========================================="
echo ""

cd /Users/navinraj/Downloads/MultiRoleUserApp

# Kill any existing process
echo "Killing existing processes on port 8080..."
lsof -ti:8080 | xargs kill -9 2>/dev/null || true
sleep 2

# Clean build
echo "Cleaning project..."
./mvnw clean -q

# Build
echo "Building project..."
./mvnw package -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✓ Build successful!"
    echo ""
    echo "Starting application..."
    java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
else
    echo "❌ Build failed"
    exit 1
fi

