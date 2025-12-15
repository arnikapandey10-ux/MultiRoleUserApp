#!/bin/bash

# Multi-Role User Authentication System - Quick Start Script

echo "=========================================="
echo "Multi-Role User Authentication System"
echo "Quick Start Script"
echo "=========================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ ERROR: Java is not installed. Please install Java 17 or higher."
    exit 1
fi

echo "✓ Java detected: $(java -version 2>&1 | head -n 1)"
echo ""

# Navigate to project directory
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "📁 Project Directory: $PROJECT_DIR"
echo ""

# Build the project
echo "🔨 Building project..."
./mvnw clean package -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✓ Build successful!"
    echo ""

    # Run the application
    echo "🚀 Starting application..."
    echo "   Server: http://localhost:8080"
    echo "   H2 Console: http://localhost:8080/h2-console"
    echo ""
    echo "📝 Default Test Users:"
    echo "   - admin / admin123 (ADMIN role)"
    echo "   - manager / manager123 (MANAGER role)"
    echo "   - user / user123 (USER role)"
    echo ""
    echo "Press Ctrl+C to stop the server"
    echo "=========================================="
    echo ""

    java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi

