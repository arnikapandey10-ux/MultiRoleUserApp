repo# Setup Guide - Multi-Role User Authentication System

## Quick Start (3 Steps)

### Step 1: Navigate to Project
```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
```

### Step 2: Build the Project
```bash
./mvnw clean package -DskipTests
```

### Step 3: Run the Application
```bash
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
```

**Expected Output:**
```
=================================================================
Initial data loaded successfully!
Test Users Created:
1. Admin User: username=admin, password=admin123, role=ADMIN
2. Manager User: username=manager, password=manager123, role=MANAGER
3. Regular User: username=user, password=user123, role=USER
=================================================================

2025-12-15T22:20:00.000+05:30  INFO ... Started MultiRoleUserAppApplication in X.XXX seconds
```

---

## System Requirements

### Required
- **Java 17+** 
  - Check: `java -version`
  - Download: https://www.oracle.com/java/technologies/downloads/
  
- **Maven 3.6+**
  - Check: `mvn -version`
  - Or use bundled `./mvnw` (Maven Wrapper)

### Optional
- **Git** (to clone the project)
- **Postman** (for API testing)
- **cURL** (for command-line API testing)

---

## Project Configuration

### Application Properties File
**Location:** `src/main/resources/application.properties`

```properties
# Application Name
spring.application.name=MultiRoleUserApp

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console Settings
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server Settings
server.port=8080
```

### Key Properties Explained

| Property | Value | Explanation |
|----------|-------|-------------|
| `spring.datasource.url` | `jdbc:h2:mem:testdb` | In-memory H2 database |
| `spring.h2.console.enabled` | `true` | Enables H2 web console |
| `spring.h2.console.path` | `/h2-console` | H2 console URL path |
| `spring.jpa.hibernate.ddl-auto` | `create-drop` | Auto-create and drop tables |
| `spring.jpa.show-sql` | `true` | Log SQL queries |
| `server.port` | `8080` | Application port |

---

## Database Access

### H2 Web Console

**URL:** http://localhost:8080/h2-console

**Credentials:**
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** (leave empty)

**Steps to Access:**
1. Start the application
2. Open browser: http://localhost:8080/h2-console
3. Enter credentials above
4. Click "Connect"
5. Run SQL queries

**Sample Queries:**
```sql
-- View all users
SELECT * FROM users;

-- View all roles
SELECT * FROM roles;

-- View user-role assignments
SELECT u.username, r.name 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id 
JOIN roles r ON ur.role_id = r.id;

-- Count users by role
SELECT r.name, COUNT(u.id) as user_count
FROM roles r
LEFT JOIN user_roles ur ON r.id = ur.role_id
LEFT JOIN users u ON ur.user_id = u.id
GROUP BY r.name;
```

---

## Maven Configuration

### POM.xml Main Sections

**Parent:**
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.0</version>
  <relativePath/>
</parent>
```

**Java Version:**
```xml
<properties>
  <java.version>17</java.version>
</properties>
```

**Compiler Plugin:**
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.11.0</version>
  <configuration>
    <source>17</source>
    <target>17</target>
    <release>17</release>
  </configuration>
</plugin>
```

### Maven Commands Reference

```bash
# Clean build (remove target directory)
./mvnw clean

# Compile project
./mvnw compile

# Run tests
./mvnw test

# Package as JAR
./mvnw package

# Package without running tests
./mvnw package -DskipTests

# Build and install in local repository
./mvnw install

# Complete rebuild
./mvnw clean install -DskipTests

# Run application directly
./mvnw spring-boot:run

# View dependency tree
./mvnw dependency:tree

# Check for dependency updates
./mvnw versions:display-dependency-updates
```

---

## File Structure Explanation

```
MultiRoleUserApp/
│
├── src/main/java/com/user/MultiRoleUserApp/
│   ├── config/
│   │   ├── SecurityConfig.java          # Spring Security configuration
│   │   └── DataInitializer.java         # Initial data loader
│   │
│   ├── controller/
│   │   ├── AuthenticationController.java # Login, register endpoints
│   │   ├── AdminController.java         # Admin-only endpoints
│   │   ├── ManagerController.java       # Manager-only endpoints
│   │   └── UserController.java          # User-accessible endpoints
│   │
│   ├── model/
│   │   ├── User.java                    # User entity
│   │   └── Role.java                    # Role entity
│   │
│   ├── repository/
│   │   ├── UserRepository.java          # User data access
│   │   └── RoleRepository.java          # Role data access
│   │
│   ├── service/
│   │   ├── AuthenticationService.java   # Auth business logic
│   │   └── CustomUserDetailsService.java # User details loader
│   │
│   ├── dto/
│   │   ├── LoginRequest.java            # Login input
│   │   ├── LoginResponse.java           # Login output
│   │   ├── UserRegistrationRequest.java # Registration input
│   │   └── ApiResponse.java             # Generic API response
│   │
│   └── MultiRoleUserAppApplication.java # Main application class
│
├── src/main/resources/
│   └── application.properties            # Configuration file
│
├── src/test/java/com/user/MultiRoleUserApp/
│   └── AuthenticationControllerTests.java # Unit tests
│
├── pom.xml                              # Maven configuration
├── README.md                            # Project documentation
├── SETUP_GUIDE.md                       # This file
└── start.sh                             # Quick start script
```

---

## Server Configuration

### Port Configuration
Default: **8080**

**To change port:**

Option 1 - Via command line:
```bash
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar --server.port=8081
```

Option 2 - Via application.properties:
```properties
server.port=8081
```

### Server Information
- **Protocol:** HTTP (no SSL by default)
- **Context Path:** / (root)
- **Base URL:** http://localhost:8080
- **Max Threads:** Default Tomcat configuration

---

## Docker Support (Optional)

To run in Docker, create a `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
# Build Docker image
docker build -t multirole-auth:1.0 .

# Run container
docker run -p 8080:8080 multirole-auth:1.0
```

---

## Troubleshooting

### Issue: "Cannot find Java"
**Solution:**
```bash
# Install Java 17
# macOS with Homebrew:
brew install openjdk@17

# Add to PATH if needed:
export PATH="/usr/local/opt/openjdk@17/bin:$PATH"
```

### Issue: "Port 8080 already in use"
**Solution:**
```bash
# Find process using port 8080
lsof -i :8080

# Kill process (macOS/Linux)
kill -9 <PID>

# Or use different port
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Issue: "Cannot find Maven"
**Solution:**
```bash
# Use provided Maven wrapper
./mvnw --version

# Or install Maven
brew install maven
```

### Issue: "H2 Console Not Accessible"
**Solution:**
- Verify application is running: http://localhost:8080/api/auth/public/health
- Check URL: http://localhost:8080/h2-console (with trailing slash)
- Verify in application.properties: `spring.h2.console.enabled=true`
- Check credentials: username=sa, password=(empty)

### Issue: "Login Failed"
**Solution:**
1. Verify application started successfully
2. Check test user exists in H2 console: `SELECT * FROM users;`
3. Verify password is correct (case-sensitive)
4. Check user is enabled: `enabled = true`

---

## Initial Data Setup

The application automatically creates initial data on startup via `DataInitializer.java`:

**Roles Created:**
- ADMIN - System administrator
- MANAGER - Team manager
- USER - Regular user

**Test Users Created:**
- admin / admin123 → ADMIN role
- manager / manager123 → MANAGER role
- user / user123 → USER role

**To Modify Initial Data:**
Edit `src/main/java/com/user/MultiRoleUserApp/config/DataInitializer.java`

---

## Environment-Specific Configuration

### Development (Current)
```properties
spring.jpa.hibernate.ddl-auto=create-drop  # Auto-create tables
spring.jpa.show-sql=true                   # Log SQL
spring.h2.console.enabled=true             # Enable console
```

### Staging
```properties
spring.jpa.hibernate.ddl-auto=update       # Update existing tables
spring.jpa.show-sql=false                  # Don't log SQL
spring.h2.console.enabled=false            # Disable console
```

### Production
```properties
spring.jpa.hibernate.ddl-auto=validate     # Validate schema only
spring.datasource.url=jdbc:postgresql://localhost:5432/db  # Real DB
spring.datasource.username=${DB_USER}      # From environment
spring.datasource.password=${DB_PASSWORD}  # From environment
```

---

## Testing

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test
```bash
./mvnw test -Dtest=AuthenticationControllerTests
```

### Test Endpoints Manually

**Using cURL:**
```bash
# Health check
curl http://localhost:8080/api/auth/public/health

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Access protected endpoint
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Using Postman:**
1. Create new collection
2. Add requests with URLs from README.md
3. Use Basic Auth with credentials
4. Set Content-Type: application/json

---

## Performance Tips

### Build Performance
```bash
# Skip tests during build (faster)
./mvnw package -DskipTests

# Use parallel builds
./mvnw -T 1C clean package

# Skip documentation
./mvnw -DskipDocs=true package
```

### Runtime Performance
- Application starts in ~5-10 seconds
- H2 in-memory database is very fast
- No external dependencies required
- Lightweight footprint suitable for development

---

## Security Notes

### For Development Only
⚠️ The following are configured for development ease:
- CSRF disabled
- CORS allows all origins
- Default test users with simple passwords
- SQL logging enabled
- H2 console exposed

### Before Production
🔒 Must be implemented:
1. Enable CSRF protection
2. Restrict CORS origins
3. Use strong passwords
4. Disable SQL logging
5. Disable H2 console
6. Use HTTPS/SSL
7. Implement JWT tokens
8. Add rate limiting
9. Configure audit logging
10. Use production database

---

## Support & Next Steps

1. **Read README.md** for detailed API documentation
2. **Access H2 Console** to explore database
3. **Test Endpoints** using cURL or Postman
4. **Review Code** to understand architecture
5. **Extend Features** for your use case

---

**Version:** 1.0.0  
**Last Updated:** December 15, 2025  
**Status:** ✅ Ready for Development & Testing

