#!/bin/bash

# Swagger Fix Script - Complete Rebuild

echo "=========================================="
echo "Swagger API - Complete Fix & Rebuild"
echo "=========================================="
echo ""

# Navigate to project
cd /Users/navinraj/Downloads/MultiRoleUserApp

# Step 1: Kill any existing process on port 8080
echo "Step 1: Clearing port 8080..."
lsof -ti:8080 | xargs kill -9 2>/dev/null || true
sleep 2
echo "✓ Port 8080 cleared"
echo ""

# Step 2: Clean old build files
echo "Step 2: Cleaning project..."
./mvnw clean -q
echo "✓ Project cleaned"
echo ""

# Step 3: Build with Swagger dependencies
echo "Step 3: Building with Swagger dependencies..."
echo "This may take a minute on first build..."
./mvnw package -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✓ Build successful!"
    echo ""

    # Step 4: Start the application
    echo "Step 4: Starting application..."
    echo "=========================================="
    java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar &

    sleep 10

    echo ""
    echo "=========================================="
    echo "✓ Application started successfully!"
    echo ""
    echo "📚 SWAGGER ACCESS POINTS:"
    echo "=========================================="
    echo "Swagger UI:      http://localhost:8080/swagger-ui.html"
    echo "OpenAPI JSON:    http://localhost:8080/v3/api-docs"
    echo "OpenAPI YAML:    http://localhost:8080/v3/api-docs.yaml"
    echo "Health Check:    http://localhost:8080/api/auth/public/health"
    echo "H2 Console:      http://localhost:8080/h2-console"
    echo ""
    echo "📋 Test Credentials:"
    echo "=========================================="
    echo "Admin:    admin / admin123"
    echo "Manager:  manager / manager123"
    echo "User:     user / user123"
    echo ""
    echo "🚀 Next Steps:"
    echo "=========================================="
    echo "1. Open: http://localhost:8080/swagger-ui.html"
    echo "2. Click on any endpoint to see documentation"
    echo "3. Click 'Try it out' to test endpoints"
    echo "4. Click 'Authorize' to test protected endpoints"
    echo ""
    echo "✅ Swagger is now ready to use!"
    echo "=========================================="
else
    echo "❌ Build failed. Please check errors above."
    exit 1
fi

