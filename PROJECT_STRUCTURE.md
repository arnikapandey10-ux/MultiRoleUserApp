# Project Structure & File Guide

## Complete Directory Tree

```
MultiRoleUserApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/user/MultiRoleUserApp/
│   │   │       ├── config/
│   │   │       │   ├── DataInitializer.java         [Initial data loader]
│   │   │       │   └── SecurityConfig.java          [Security configuration]
│   │   │       ├── controller/
│   │   │       │   ├── AdminController.java         [Admin endpoints]
│   │   │       │   ├── AuthenticationController.java [Auth endpoints]
│   │   │       │   ├── ManagerController.java       [Manager endpoints]
│   │   │       │   └── UserController.java          [User endpoints]
│   │   │       ├── dto/
│   │   │       │   ├── ApiResponse.java             [Generic API response]
│   │   │       │   ├── LoginRequest.java            [Login input DTO]
│   │   │       │   ├── LoginResponse.java           [Login output DTO]
│   │   │       │   └── UserRegistrationRequest.java [Registration input DTO]
│   │   │       ├── model/
│   │   │       │   ├── Role.java                    [Role entity]
│   │   │       │   └── User.java                    [User entity]
│   │   │       ├── repository/
│   │   │       │   ├── RoleRepository.java          [Role data access]
│   │   │       │   └── UserRepository.java          [User data access]
│   │   │       ├── service/
│   │   │       │   ├── AuthenticationService.java   [Auth business logic]
│   │   │       │   └── CustomUserDetailsService.java[User details loader]
│   │   │       └── MultiRoleUserAppApplication.java [Main application class]
│   │   └── resources/
│   │       └── application.properties                [Application configuration]
│   └── test/
│       └── java/
│           └── com/user/MultiRoleUserApp/
│               └── AuthenticationControllerTests.java [Unit tests]
├── target/                                           [Build output (auto-generated)]
├── .m2/                                              [Maven cache (auto-generated)]
├── pom.xml                                           [Maven configuration]
├── mvnw                                              [Maven wrapper (Linux/Mac)]
├── mvnw.cmd                                          [Maven wrapper (Windows)]
├── README.md                                         [Project documentation]
├── SETUP_GUIDE.md                                    [Setup instructions]
├── API_REFERENCE.md                                  [API documentation]
├── PROJECT_STRUCTURE.md                              [This file]
└── start.sh                                          [Quick start script]
```

---

## File Descriptions

### Core Application Files

#### `MultiRoleUserAppApplication.java`
- **Package:** `com.user.MultiRoleUserApp`
- **Purpose:** Main Spring Boot application entry point
- **Key Components:**
  - Main method to bootstrap the application
  - `@SpringBootApplication` annotation enables auto-configuration
  - Contains documentation comments

#### `pom.xml`
- **Purpose:** Maven project configuration
- **Contains:**
  - Project metadata (groupId, artifactId, version)
  - Spring Boot parent configuration (version 4.0.0)
  - Dependency declarations
  - Build plugins configuration
  - Java version specification (17)

#### `application.properties`
- **Location:** `src/main/resources/`
- **Purpose:** Application configuration
- **Contains:**
  - H2 database configuration
  - JPA/Hibernate settings
  - Server port configuration
  - Logging configuration

---

### Configuration Layer

#### `SecurityConfig.java`
- **Package:** `com.user.MultiRoleUserApp.config`
- **Purpose:** Spring Security configuration
- **Key Methods:**
  - `passwordEncoder()` - BCrypt password encoder bean
  - `authenticationManager()` - Authentication manager bean
  - `filterChain()` - HTTP security configuration
- **Configures:**
  - CSRF protection (disabled for API)
  - Session management (IF_REQUIRED)
  - Authorization rules by endpoint
  - HTTP Basic authentication
  - CORS settings

#### `DataInitializer.java`
- **Package:** `com.user.MultiRoleUserApp.config`
- **Purpose:** Initialize database with sample data on startup
- **Implements:** `CommandLineRunner`
- **Creates:**
  - Three roles: ADMIN, MANAGER, USER
  - Three test users with default passwords
  - User-role associations
- **Prints startup information to console**

---

### Controller Layer

#### `AuthenticationController.java`
- **Package:** `com.user.MultiRoleUserApp.controller`
- **Base Path:** `/api/auth`
- **Endpoints:**
  - `GET /public/health` - Health check
  - `POST /login` - User login
  - `POST /register` - User registration
  - `POST /logout` - User logout
- **Handles:** Authentication and user registration

#### `AdminController.java`
- **Package:** `com.user.MultiRoleUserApp.controller`
- **Base Path:** `/api/admin`
- **Endpoints:**
  - `GET /dashboard` - Admin dashboard
  - `GET /users` - List all users
  - `GET /statistics` - System statistics
- **Authorization:** Requires ADMIN role
- **Annotation:** `@PreAuthorize("hasRole('ADMIN')")`

#### `ManagerController.java`
- **Package:** `com.user.MultiRoleUserApp.controller`
- **Base Path:** `/api/manager`
- **Endpoints:**
  - `GET /dashboard` - Manager dashboard
  - `GET /team` - Team members
  - `GET /reports` - Reports
- **Authorization:** Requires MANAGER role
- **Annotation:** `@PreAuthorize("hasRole('MANAGER')")`

#### `UserController.java`
- **Package:** `com.user.MultiRoleUserApp.controller`
- **Base Path:** `/api/user`
- **Endpoints:**
  - `GET /profile` - User profile
  - `GET /dashboard` - User dashboard
  - `GET /settings` - User settings
- **Authorization:** Requires USER role or higher
- **Annotation:** `@PreAuthorize("hasRole('USER')")`

---

### Model/Entity Layer

#### `User.java`
- **Package:** `com.user.MultiRoleUserApp.model`
- **Table:** `users`
- **Implements:** `UserDetails` (Spring Security interface)
- **Fields:**
  - `id` (Primary Key, auto-generated)
  - `username` (Unique, not null)
  - `password` (Hashed with BCrypt)
  - `email` (Not null)
  - `firstName`, `lastName` (Optional)
  - `enabled`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`
- **Relationships:**
  - ManyToMany with Role via `user_roles` join table
- **Methods:**
  - `getAuthorities()` - Returns roles as GrantedAuthority
  - Account status checks (isEnabled, isAccountNonLocked, etc.)

#### `Role.java`
- **Package:** `com.user.MultiRoleUserApp.model`
- **Table:** `roles`
- **Fields:**
  - `id` (Primary Key, auto-generated)
  - `name` (Unique, not null) - e.g., "ADMIN", "MANAGER", "USER"
  - `description` (Optional)
- **Relationships:**
  - ManyToMany with User via `user_roles` join table

---

### Repository Layer

#### `UserRepository.java`
- **Package:** `com.user.MultiRoleUserApp.repository`
- **Extends:** `JpaRepository<User, Long>`
- **Custom Methods:**
  - `findByUsername(String username)` - Find user by username
  - `findByEmail(String email)` - Find user by email
  - `existsByUsername(String username)` - Check if username exists
- **Database Access:** Spring Data JPA handles SQL generation

#### `RoleRepository.java`
- **Package:** `com.user.MultiRoleUserApp.repository`
- **Extends:** `JpaRepository<Role, Long>`
- **Custom Methods:**
  - `findByName(String name)` - Find role by name
- **Database Access:** Spring Data JPA handles SQL generation

---

### Service Layer

#### `AuthenticationService.java`
- **Package:** `com.user.MultiRoleUserApp.service`
- **Purpose:** Core authentication and authorization logic
- **Key Methods:**
  - `registerUser(UserRegistrationRequest)` - Register new user
  - `authenticateUser(LoginRequest)` - Authenticate user
  - `getUserByUsername(String)` - Retrieve user details
  - `getUserById(Long)` - Retrieve user by ID
  - `userHasRole(User, String)` - Check user role
  - `updateUserRoles(Long, Set<String>)` - Update user roles
- **Returns:** LoginResponse objects with success/failure status

#### `CustomUserDetailsService.java`
- **Package:** `com.user.MultiRoleUserApp.service`
- **Implements:** `UserDetailsService` (Spring Security interface)
- **Purpose:** Load user details for Spring Security
- **Methods:**
  - `loadUserByUsername(String)` - Load user by username
  - Throws `UsernameNotFoundException` if user not found
- **Integration:** Used by `DaoAuthenticationProvider`

---

### Data Transfer Objects (DTOs)

#### `LoginRequest.java`
- **Package:** `com.user.MultiRoleUserApp.dto`
- **Purpose:** Input DTO for login endpoint
- **Fields:**
  - `username` (Required)
  - `password` (Required)
- **Usage:** HTTP request body for `/api/auth/login`

#### `LoginResponse.java`
- **Package:** `com.user.MultiRoleUserApp.dto`
- **Purpose:** Output DTO for authentication responses
- **Fields:**
  - `message` (String) - Status message
  - `success` (Boolean) - Success flag
  - `userId` (Long) - User ID
  - `username` (String) - Username
  - `email` (String) - User email
  - `roles` (Set<String>) - User roles
- **Usage:** HTTP response body for login/registration endpoints

#### `UserRegistrationRequest.java`
- **Package:** `com.user.MultiRoleUserApp.dto`
- **Purpose:** Input DTO for user registration
- **Fields:**
  - `username` (Required)
  - `password` (Required)
  - `email` (Required)
  - `firstName` (Optional)
  - `lastName` (Optional)
  - `roleNames` (Required) - Set of role names to assign
- **Usage:** HTTP request body for `/api/auth/register`

#### `ApiResponse.java`
- **Package:** `com.user.MultiRoleUserApp.dto`
- **Purpose:** Generic API response DTO
- **Fields:**
  - `message` (String) - Response message
  - `success` (Boolean) - Success flag
  - `data` (Object) - Response payload (optional)
- **Usage:** Response body for various endpoints
- **Constructors:**
  - With and without data field

---

### Test Files

#### `AuthenticationControllerTests.java`
- **Location:** `src/test/java/com/user/MultiRoleUserApp/`
- **Framework:** JUnit 5, Spring Boot Test
- **Test Cases:**
  - Health endpoint access
  - Login with valid credentials
  - Login with invalid credentials
  - User registration
  - Admin endpoint access control
  - Manager endpoint access control
  - User endpoint access control
  - Unauthenticated access denial
- **Annotations:**
  - `@SpringBootTest` - Full Spring context
  - `@WithMockUser` - Simulate authenticated users
- **Usage:** `./mvnw test`

---

### Build Files

#### `mvnw` / `mvnw.cmd`
- **Purpose:** Maven Wrapper
- **Function:** Allows building without Maven installed
- **Usage:**
  - Linux/Mac: `./mvnw clean package`
  - Windows: `mvnw.cmd clean package`
- **Benefits:**
  - Consistent Maven version across machines
  - No external Maven installation required

---

### Documentation Files

#### `README.md`
- **Purpose:** Project overview and comprehensive documentation
- **Contents:**
  - Technology stack
  - Dependencies
  - Getting started guide
  - API endpoints
  - Database configuration
  - Security configuration
  - Testing instructions
  - Troubleshooting

#### `SETUP_GUIDE.md`
- **Purpose:** Step-by-step setup and configuration guide
- **Contents:**
  - System requirements
  - Installation steps
  - Project configuration details
  - Maven commands
  - H2 database access
  - Environment-specific configuration
  - Performance tips

#### `API_REFERENCE.md`
- **Purpose:** Complete API documentation with examples
- **Contents:**
  - Base URL and authentication methods
  - All endpoint descriptions with examples
  - Request/response formats
  - Error responses
  - cURL examples
  - Postman integration guide

#### `start.sh`
- **Purpose:** Quick start script
- **Function:** Automates build and startup process
- **Usage:** `./start.sh`
- **Performs:**
  - Java version check
  - Project compilation
  - JAR execution
  - Server startup with information

---

## Layer Architecture

```
┌─────────────────────────────────────────────┐
│         HTTP / REST API Layer               │
│     (Browser, cURL, Postman, etc.)          │
└─────────────────────────────────────────────┘
              ▼
┌─────────────────────────────────────────────┐
│       Controller Layer                       │
│  (AuthenticationController,                  │
│   AdminController, etc.)                    │
└─────────────────────────────────────────────┘
              ▼
┌─────────────────────────────────────────────┐
│        Service Layer                         │
│  (AuthenticationService,                    │
│   CustomUserDetailsService)                 │
└─────────────────────────────────────────────┘
              ▼
┌─────────────────────────────────────────────┐
│      Repository Layer (Data Access)         │
│  (UserRepository, RoleRepository)           │
└─────────────────────────────────────────────┘
              ▼
┌─────────────────────────────────────────────┐
│       Model/Entity Layer                    │
│  (User, Role - JPA Entities)               │
└─────────────────────────────────────────────┘
              ▼
┌─────────────────────────────────────────────┐
│    Database Layer (H2 Database)             │
│  (users, roles, user_roles tables)          │
└─────────────────────────────────────────────┘
```

---

## Data Flow Examples

### Login Flow
```
User Browser Request
    ▼
AuthenticationController.login()
    ▼
AuthenticationService.authenticateUser()
    ▼
PasswordEncoder.matches()
    ▼
Database Lookup
    ▼
LoginResponse Created
    ▼
Browser Receives Response
```

### Protected Endpoint Access Flow
```
Authenticated Request
    ▼
SecurityFilterChain Validation
    ▼
@PreAuthorize Annotation Check
    ▼
User Has Required Role?
    ├─ YES → Controller Method Executes → Response 200 OK
    └─ NO → AccessDeniedException → Response 403 Forbidden
```

---

## Key Technologies by Package

### `model/`
- **JPA** (Jakarta Persistence API)
- **Hibernate** (ORM)
- **Spring Security** (UserDetails interface)

### `repository/`
- **Spring Data JPA**
- **JPA Repository**
- **Hibernate Query Generation**

### `service/`
- **Spring Framework** (Dependency Injection)
- **Spring Security** (UserDetailsService)

### `controller/`
- **Spring Web** (MVC Framework)
- **Spring Security** (PreAuthorize)
- **Jackson** (JSON serialization)

### `config/`
- **Spring Security** (Security Configuration)
- **Spring Boot** (Auto-configuration)
- **Hibernate** (ORM configuration)

---

## Code Organization Principles

1. **Separation of Concerns**
   - Controller handles HTTP
   - Service handles business logic
   - Repository handles data access
   - Entity defines domain model

2. **Dependency Injection**
   - All dependencies injected via `@Autowired`
   - No manual object creation
   - Testable and loosely coupled

3. **RESTful Design**
   - Resource-oriented endpoints
   - Standard HTTP verbs (GET, POST)
   - Meaningful paths
   - Standard status codes

4. **Security First**
   - BCrypt password encoding
   - Role-based access control
   - Method-level security
   - Spring Security integration

---

## Build Artifacts

### Generated Files (Auto-generated, not committed)

```
target/
├── classes/                      # Compiled Java classes
├── test-classes/                 # Compiled test classes
├── MultiRoleUserApp-0.0.1-SNAPSHOT.jar       # Executable JAR
├── MultiRoleUserApp-0.0.1-SNAPSHOT.jar.original
└── maven-status/

.m2/
└── repository/                   # Maven dependency cache
```

---

## File Dependencies

### `pom.xml` Dependencies
```
Spring Boot 4.0.0
├── spring-boot-starter-web
├── spring-boot-starter-security
├── spring-boot-starter-data-jpa
├── spring-boot-h2console
├── h2 (database)
├── jackson-databind (JSON)
├── spring-boot-starter-test (testing)
└── spring-security-test (testing)
```

### Internal Dependencies
```
MultiRoleUserAppApplication.java
└── SecurityConfig.java
    └── CustomUserDetailsService.java
        └── UserRepository.java
            └── User.java
                └── Role.java

AuthenticationController.java
└── AuthenticationService.java
    ├── UserRepository.java
    ├── RoleRepository.java
    └── PasswordEncoder (from SecurityConfig)

AdminController.java
├── ApiResponse.java
└── (Secured by SecurityConfig)
```

---

## Modification Guide

### To Add New Endpoint
1. Create method in appropriate Controller
2. Add `@PreAuthorize` if role-based
3. Add DTOs if needed
4. Add tests in test file
5. Document in API_REFERENCE.md

### To Add New Role
1. Create Role in DataInitializer.java
2. Add role assignment in user creation
3. Create new Controller with endpoints
4. Add `@PreAuthorize` with role name
5. Test with test user

### To Change Database
1. Update pom.xml with new database driver
2. Modify application.properties with JDBC URL
3. Update spring.jpa.hibernate.ddl-auto to 'validate' or 'update'
4. Update spring.jpa.database-platform dialect
5. Test data initialization

---

## Common File Locations

| Purpose | Location |
|---------|----------|
| Source Code | `src/main/java/com/user/MultiRoleUserApp/` |
| Tests | `src/test/java/com/user/MultiRoleUserApp/` |
| Configuration | `src/main/resources/application.properties` |
| Build Output | `target/` |
| Dependencies | `pom.xml` |
| Documentation | `README.md`, `SETUP_GUIDE.md`, `API_REFERENCE.md` |

---

**Version:** 1.0.0  
**Last Updated:** December 15, 2025

