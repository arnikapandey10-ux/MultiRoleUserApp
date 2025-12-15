# Multi-Role User Authentication System

A comprehensive Spring Boot application demonstrating a complete authentication system with role-based access control (RBAC). This project provides a foundation for building secure multi-tenant applications with user authentication, role management, and role-based endpoint protection.

## 📋 Project Overview

This application implements:
- **User Authentication** - Login/registration with password encryption using BCrypt
- **Role-Based Access Control (RBAC)** - Three default roles (ADMIN, MANAGER, USER)
- **Spring Security Integration** - Method-level security with `@PreAuthorize`
- **In-Memory Database** - H2 database for easy testing and local development
- **RESTful API** - Complete API endpoints for authentication and role-based access
- **Session Management** - HTTP Session-based authentication

## 🏗️ Project Architecture

```
MultiRoleUserApp
├── src/
│   ├── main/
│   │   ├── java/com/user/MultiRoleUserApp/
│   │   │   ├── controller/           # REST Controllers
│   │   │   │   ├── AuthenticationController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── ManagerController.java
│   │   │   │   └── UserController.java
│   │   │   ├── model/               # JPA Entities
│   │   │   │   ├── User.java
│   │   │   │   └── Role.java
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── RoleRepository.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── AuthenticationService.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── LoginResponse.java
│   │   │   │   ├── UserRegistrationRequest.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── config/              # Configuration Classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── DataInitializer.java
│   │   │   └── MultiRoleUserAppApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/user/MultiRoleUserApp/
│           └── AuthenticationControllerTests.java
├── pom.xml
└── README.md
```

## 🔧 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 4.0.0 |
| Security | Spring Security | 6.x+ |
| Database | H2 Database | In-Memory |
| ORM | Hibernate/JPA | 7.x+ |
| Build Tool | Maven | 3.9+ |
| Java Version | Java | 17+ |
| Password Encoding | BCrypt | - |

## 📦 Key Dependencies

```xml
<!-- Spring Boot Web & Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Data Access -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- JSON Processing -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 🚀 Getting Started

### Prerequisites
- **Java 17+** - Required JDK version
- **Maven 3.6+** - Build tool
- **Git** - Version control (optional, for cloning)

### Installation & Setup

1. **Clone or Download the Project**
   ```bash
   cd /Users/navinraj/Downloads/MultiRoleUserApp
   ```

2. **Build the Project**
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. **Run the Application**
   ```bash
   java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
   ```

   Or using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Verify Application Started**
   - Check console for: `Initial data loaded successfully!`
   - Application runs on: **http://localhost:8080**

## 🔐 Default Test Users

The application automatically initializes with three test users on startup:

| Username | Password | Role | Email | Access Level |
|----------|----------|------|-------|--------------|
| `admin` | `admin123` | ADMIN | admin@example.com | Full system access, user management |
| `manager` | `manager123` | MANAGER | manager@example.com | Team management, reports |
| `user` | `user123` | USER | user@example.com | Basic profile access |

## 📱 API Endpoints

### Authentication Endpoints

#### 1. Health Check (Public)
```http
GET /api/auth/public/health
```
**Response:**
```json
{
  "message": "Authentication service is running",
  "success": true
}
```

#### 2. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```
**Response (Success):**
```json
{
  "message": "Login successful",
  "success": true,
  "userId": 1,
  "username": "admin",
  "email": "admin@example.com",
  "roles": ["ADMIN"]
}
```

#### 3. Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "email": "newuser@example.com",
  "firstName": "New",
  "lastName": "User",
  "roleNames": ["USER"]
}
```
**Response:**
```json
{
  "message": "User registered successfully",
  "success": true,
  "userId": 4,
  "username": "newuser",
  "email": "newuser@example.com",
  "roles": ["USER"]
}
```

#### 4. Logout
```http
POST /api/auth/logout
```

### Admin Endpoints (ADMIN Role Required)

#### Admin Dashboard
```http
GET /api/admin/dashboard
Authorization: Basic admin:admin123
```
**Response:**
```json
{
  "message": "Welcome to Admin Dashboard",
  "success": true,
  "data": "You have admin access to system configuration, user management, and audit logs"
}
```

#### List All Users
```http
GET /api/admin/users
Authorization: Basic admin:admin123
```

#### System Statistics
```http
GET /api/admin/statistics
Authorization: Basic admin:admin123
```

### Manager Endpoints (MANAGER Role Required)

#### Manager Dashboard
```http
GET /api/manager/dashboard
Authorization: Basic manager:manager123
```

#### Team Members
```http
GET /api/manager/team
Authorization: Basic manager:manager123
```

#### Reports
```http
GET /api/manager/reports
Authorization: Basic manager:manager123
```

### User Endpoints (USER Role Required)

#### User Profile
```http
GET /api/user/profile
Authorization: Basic user:user123
```

#### User Dashboard
```http
GET /api/user/dashboard
Authorization: Basic user:user123
```

#### User Settings
```http
GET /api/user/settings
Authorization: Basic user:user123
```

## 💾 Database Configuration

### H2 Database Access

The application uses an **in-memory H2 database** for development and testing.

#### H2 Console Web Interface
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (leave blank)

#### Tables Created
1. **users** - Stores user accounts
   ```sql
   CREATE TABLE users (
     id BIGINT PRIMARY KEY,
     username VARCHAR(255) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     email VARCHAR(255) NOT NULL,
     first_name VARCHAR(255),
     last_name VARCHAR(255),
     enabled BOOLEAN DEFAULT true,
     account_non_expired BOOLEAN DEFAULT true,
     account_non_locked BOOLEAN DEFAULT true,
     credentials_non_expired BOOLEAN DEFAULT true
   );
   ```

2. **roles** - Stores role definitions
   ```sql
   CREATE TABLE roles (
     id BIGINT PRIMARY KEY,
     name VARCHAR(255) UNIQUE NOT NULL,
     description VARCHAR(255)
   );
   ```

3. **user_roles** - Join table for many-to-many relationship
   ```sql
   CREATE TABLE user_roles (
     user_id BIGINT,
     role_id BIGINT,
     PRIMARY KEY (user_id, role_id),
     FOREIGN KEY (user_id) REFERENCES users(id),
     FOREIGN KEY (role_id) REFERENCES roles(id)
   );
   ```

### Application Properties

**File:** `src/main/resources/application.properties`

```properties
# Application Name
spring.application.name=MultiRoleUserApp

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080
```

**Key Configurations:**
- `ddl-auto=create-drop` - Automatically creates tables on startup and drops them on shutdown
- `show-sql=true` - Logs generated SQL queries
- `format_sql=true` - Formats SQL for readability

## 🔒 Security Configuration

### Spring Security Features

1. **CSRF Protection** - Disabled for API testing convenience (can be enabled for production)
2. **Session Management** - Uses `IF_REQUIRED` strategy for optimal session handling
3. **HTTP Basic Authentication** - Enabled for API authentication
4. **Method-Level Security** - `@PreAuthorize` annotations for granular control
5. **Password Encoding** - BCrypt with 10 rounds of hashing
6. **Cross-Origin (CORS)** - Enabled for all origins (configure as needed)

### Security Rules

```java
// Public Access
- GET  /api/auth/login
- POST /api/auth/register
- GET  /api/auth/public/**
- GET  /h2-console/**

// ADMIN Role Only
- GET  /api/admin/**

// MANAGER Role Only
- GET  /api/manager/**

// USER Role (and higher)
- GET  /api/user/**

// All Other Endpoints
- Requires Authentication
```

## 🧪 Testing the Application

### Using cURL

```bash
# Health Check
curl http://localhost:8080/api/auth/public/health

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Access Admin Endpoint
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

# Register New User
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username":"testuser",
    "password":"test123",
    "email":"test@example.com",
    "firstName":"Test",
    "lastName":"User",
    "roleNames":["USER"]
  }'
```

### Using Postman

1. Import the endpoints or create them manually
2. Use **Basic Auth** with username and password
3. Set `Content-Type: application/json` for POST requests
4. Test role-based access by switching between users

### Using Java Unit Tests

```bash
./mvnw test
```

Test file location: `src/test/java/com/user/MultiRoleUserApp/AuthenticationControllerTests.java`

## 📊 Entity Relationships

### User Entity
- Implements `UserDetails` interface from Spring Security
- Many-to-Many relationship with Role
- Fields: id, username, password, email, firstName, lastName, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired

### Role Entity
- Simple entity with id, name, and description
- Represents access level or permission group
- Pre-populated with ADMIN, MANAGER, USER roles

### User-Role Relationship
- ManyToMany with FetchType.EAGER
- Join table: user_roles
- No cascade persist (roles must be created separately)

## 🔄 Authentication Flow

```
1. Client sends credentials (username, password) to /api/auth/login
   ↓
2. AuthenticationController receives request
   ↓
3. AuthenticationManager validates credentials
   ↓
4. CustomUserDetailsService loads user details
   ↓
5. PasswordEncoder compares passwords using BCrypt
   ↓
6. AuthenticationService prepares response with roles
   ↓
7. HTTP Session created and User logged in
   ↓
8. Client receives LoginResponse with user details and roles
   ↓
9. Subsequent requests include session cookie or Basic Auth header
   ↓
10. SecurityFilterChain validates authority and grants/denies access
```

## 📝 Role-Based Access Control Flow

```
Request to protected endpoint (e.g., /api/admin/dashboard)
   ↓
@PreAuthorize("hasRole('ADMIN')") annotation checks
   ↓
Spring Security evaluates current user's roles
   ↓
User has ADMIN role? 
   ├─ YES → Endpoint logic executes → 200 OK
   └─ NO → AccessDeniedException → 403 Forbidden
```

## 🛠️ Project Configuration Details

### POM.xml Key Sections

```xml
<!-- Spring Boot Parent -->
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.0</version>
</parent>

<!-- Java Version -->
<properties>
  <java.version>17</java.version>
</properties>

<!-- Maven Compiler Plugin -->
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

### Maven Commands

```bash
# Build without tests
./mvnw clean package -DskipTests

# Build with tests
./mvnw clean package

# Run tests only
./mvnw test

# Run application via Maven
./mvnw spring-boot:run

# Check dependencies
./mvnw dependency:tree

# Format code
./mvnw formatter:format
```

## 🔍 Logging & Debugging

### Enable SQL Logging
Already enabled in `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Enable Debug Mode
Add to `application.properties`:
```properties
logging.level.root=INFO
logging.level.com.user.MultiRoleUserApp=DEBUG
logging.level.org.springframework.security=DEBUG
```

### Application Logs
- Check console output for startup messages
- H2 database initialization logs
- SQL query execution logs
- Authentication/authorization events

## 🐛 Troubleshooting

### Issue: Port 8080 Already in Use
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9

# Or use different port
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Issue: Database Connection Fails
- Verify H2 dependency is in pom.xml
- Check application.properties JDBC URL is correct
- Ensure `spring.jpa.hibernate.ddl-auto=create-drop`

### Issue: Login Fails
- Verify username and password are correct (case-sensitive)
- Check user account is enabled in database
- Verify role assignment in user_roles table

### Issue: Access Denied (403 Forbidden)
- Confirm user has required role
- Check @PreAuthorize annotation uses correct role name
- Verify role name matches in database (case-sensitive)

### Issue: H2 Console Not Accessible
- Verify `/h2-console/**` is in permitAll() in SecurityConfig
- Check `spring.h2.console.enabled=true` in application.properties
- Ensure `/h2-console/` path is correct in URL

## 📚 Key Code Examples

### Creating a Protected Endpoint
```java
@GetMapping("/protected")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse> protectedEndpoint() {
    return ResponseEntity.ok(new ApiResponse("This is protected", true));
}
```

### Checking User Roles Programmatically
```java
@PostMapping("/complex-action")
public ResponseEntity<?> complexAction(Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName()).orElse(null);
    
    boolean isAdmin = authenticationService.userHasRole(user, "ADMIN");
    
    if (isAdmin) {
        // Perform admin action
    }
}
```

### Assigning Roles to New User
```java
UserRegistrationRequest request = new UserRegistrationRequest();
request.setUsername("newuser");
request.setPassword("password");
request.setEmail("newuser@example.com");
request.setRoleNames(Set.of("USER", "MANAGER")); // Multiple roles

LoginResponse response = authenticationService.registerUser(request);
```

## 🚀 Deployment Considerations

### For Production
1. Change `ddl-auto` from `create-drop` to `validate` or `update`
2. Use persistent database (PostgreSQL, MySQL) instead of H2
3. Enable CSRF protection
4. Implement JWT tokens for stateless authentication
5. Add HTTPS/SSL configuration
6. Configure environment-specific properties
7. Implement proper password reset mechanism
8. Add audit logging
9. Implement rate limiting
10. Add comprehensive error handling

### Example Production Properties
```properties
# Production Database
spring.datasource.url=jdbc:postgresql://localhost:5432/multiroledb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Security
server.ssl.key-store=${SSL_KEYSTORE_PATH}
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12
```

## 📖 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [H2 Database Documentation](https://www.h2database.com/)
- [Hibernate Documentation](https://hibernate.org/)

## 📄 License

This project is provided as-is for educational and development purposes.

## 👨‍💻 Contributing

This is a reference implementation. Feel free to extend it with:
- JWT token-based authentication
- OAuth2 integration
- API documentation (Swagger/OpenAPI)
- Email verification
- Two-factor authentication
- Advanced audit logging
- User permission management (beyond roles)

## 📞 Support

For issues or questions:
1. Check the Troubleshooting section above
2. Review application logs
3. Test with H2 Console to verify data
4. Use Postman or cURL to test endpoints directly

---

**Project Status:** ✅ Complete and Ready for Testing

**Last Updated:** December 15, 2025

