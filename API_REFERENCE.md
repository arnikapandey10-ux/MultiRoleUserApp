# API Reference - Multi-Role User Authentication System

## Base URL
```
http://localhost:8080
```

## Authentication
The API supports two authentication methods:

### 1. HTTP Basic Auth
```
Authorization: Basic base64(username:password)
```

### 2. Session-Based (After Login)
Cookie-based session authentication after successful login.

---

## Authentication Endpoints

### 1. Health Check
Check if authentication service is running.

```http
GET /api/auth/public/health
```

**Authentication:** None (Public)

**Response (200 OK):**
```json
{
  "message": "Authentication service is running",
  "success": true
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/auth/public/health
```

---

### 2. Login
Authenticate user and retrieve user details with roles.

```http
POST /api/auth/login
Content-Type: application/json
```

**Authentication:** None (Public)

**Request Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response (200 OK) - Success:**
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

**Response (401 Unauthorized) - Invalid Credentials:**
```json
{
  "message": "Invalid username or password",
  "success": false,
  "userId": null,
  "username": null,
  "email": null,
  "roles": null
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Postman Example:**
- Method: POST
- URL: http://localhost:8080/api/auth/login
- Headers: Content-Type: application/json
- Body (raw JSON):
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```

---

### 3. Register User
Create a new user account with assigned roles.

```http
POST /api/auth/register
Content-Type: application/json
```

**Authentication:** None (Public)

**Request Body:**
```json
{
  "username": "newuser",
  "password": "securePassword123",
  "email": "newuser@example.com",
  "firstName": "New",
  "lastName": "User",
  "roleNames": ["USER"]
}
```

**Response (201 Created) - Success:**
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

**Response (400 Bad Request) - User Exists:**
```json
{
  "message": "User already exists with username: newuser",
  "success": false,
  "userId": null,
  "username": null,
  "email": null,
  "roles": null
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "securePassword123",
    "email": "newuser@example.com",
    "firstName": "New",
    "lastName": "User",
    "roleNames": ["USER"]
  }'
```

**Available Roles for Assignment:**
- `USER` - Basic user access
- `MANAGER` - Manager level access
- `ADMIN` - Administrator level access

**Validation Rules:**
- Username: Required, must be unique, alphanumeric
- Password: Required, minimum 6 characters recommended
- Email: Required, valid email format
- First Name: Optional
- Last Name: Optional
- Roles: Required, at least one role must be assigned

---

### 4. Logout
Terminate user session.

```http
POST /api/auth/logout
Authorization: Basic base64(username:password)
```

**Authentication:** Required (Basic Auth or Session)

**Response (200 OK):**
```json
{
  "message": "Logout successful",
  "success": true
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

---

## Admin Endpoints

### 1. Admin Dashboard
Access main admin dashboard.

```http
GET /api/admin/dashboard
Authorization: Basic base64(username:password)
```

**Authentication:** Required (ADMIN Role)

**Response (200 OK):**
```json
{
  "message": "Welcome to Admin Dashboard",
  "success": true,
  "data": "You have admin access to system configuration, user management, and audit logs"
}
```

**Response (403 Forbidden) - Insufficient Permission:**
```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/admin/dashboard"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

---

### 2. List All Users
View all registered users (Admin only).

```http
GET /api/admin/users
Authorization: Basic base64(username:password)
```

**Authentication:** Required (ADMIN Role)

**Response (200 OK):**
```json
{
  "message": "Fetching all users",
  "success": true,
  "data": "Admin can view all users in the system"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/admin/users \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

---

### 3. System Statistics
View system statistics and health.

```http
GET /api/admin/statistics
Authorization: Basic base64(username:password)
```

**Authentication:** Required (ADMIN Role)

**Response (200 OK):**
```json
{
  "message": "System Statistics",
  "success": true,
  "data": "Total Users: 3, Active Sessions: 1, System Health: Good"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/admin/statistics \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

---

## Manager Endpoints

### 1. Manager Dashboard
Access manager dashboard.

```http
GET /api/manager/dashboard
Authorization: Basic base64(username:password)
```

**Authentication:** Required (MANAGER Role or higher)

**Response (200 OK):**
```json
{
  "message": "Welcome to Manager Dashboard",
  "success": true,
  "data": "You have access to team management, reports, and project oversight"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/manager/dashboard \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"
```

---

### 2. Team Members
View and manage team members.

```http
GET /api/manager/team
Authorization: Basic base64(username:password)
```

**Authentication:** Required (MANAGER Role or higher)

**Response (200 OK):**
```json
{
  "message": "Team Members",
  "success": true,
  "data": "Manager can view and manage team members"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/manager/team \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"
```

---

### 3. Reports
Generate and view reports.

```http
GET /api/manager/reports
Authorization: Basic base64(username:password)
```

**Authentication:** Required (MANAGER Role or higher)

**Response (200 OK):**
```json
{
  "message": "Reports",
  "success": true,
  "data": "Manager can access team performance and project reports"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/manager/reports \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"
```

---

## User Endpoints

### 1. User Profile
Get user profile information.

```http
GET /api/user/profile
Authorization: Basic base64(username:password)
```

**Authentication:** Required (USER Role or higher)

**Response (200 OK):**
```json
{
  "message": "User Profile",
  "success": true,
  "data": "Username: user, Role: USER"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/user/profile \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

---

### 2. User Dashboard
Access user dashboard.

```http
GET /api/user/dashboard
Authorization: Basic base64(username:password)
```

**Authentication:** Required (USER Role or higher)

**Response (200 OK):**
```json
{
  "message": "Welcome to User Dashboard",
  "success": true,
  "data": "You have access to view your profile and personal settings"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/user/dashboard \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

---

### 3. User Settings
Access user settings and preferences.

```http
GET /api/user/settings
Authorization: Basic base64(username:password)
```

**Authentication:** Required (USER Role or higher)

**Response (200 OK):**
```json
{
  "message": "User Settings",
  "success": true,
  "data": "You can update your profile, password, and preferences"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/user/settings \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

---

## Error Responses

### 400 Bad Request
Invalid request format or missing required fields.

```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/auth/register"
}
```

### 401 Unauthorized
Missing or invalid authentication credentials.

```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials",
  "path": "/api/admin/dashboard"
}
```

### 403 Forbidden
User authenticated but lacks required role/permission.

```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/admin/dashboard"
}
```

### 500 Internal Server Error
Server error during request processing.

```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An error occurred",
  "path": "/api/auth/login"
}
```

---

## Default Test Credentials

| Username | Password | Role | Endpoints |
|----------|----------|------|-----------|
| `admin` | `admin123` | ADMIN | /api/admin/*, /api/manager/*, /api/user/* |
| `manager` | `manager123` | MANAGER | /api/manager/*, /api/user/* |
| `user` | `user123` | USER | /api/user/* |

---

## HTTP Status Codes

| Code | Meaning | Typical Use |
|------|---------|------------|
| 200 | OK | Successful GET/POST request |
| 201 | Created | Successful resource creation (POST) |
| 400 | Bad Request | Invalid request format |
| 401 | Unauthorized | Missing/invalid authentication |
| 403 | Forbidden | Authenticated but no permission |
| 404 | Not Found | Endpoint/resource not found |
| 500 | Server Error | Internal server error |

---

## Rate Limiting
Not implemented in current version. To add:
```yaml
# Add to pom.xml
- spring-cloud-starter-circuitbreaker-resilience4j
- io.github.resilience4j:resilience4j-ratelimiter
```

---

## CORS Configuration

**Allowed Origins:** * (All origins)  
**Allowed Methods:** GET, POST, PUT, DELETE, OPTIONS  
**Allowed Headers:** * (All headers)

To restrict in production, modify `SecurityConfig.java`:
```java
@CrossOrigin(origins = "https://yourdomain.com", allowedHeaders = "*")
```

---

## Content-Type
All endpoints require and return:
```
Content-Type: application/json
```

---

## Examples by Use Case

### 1. Complete Login Flow
```bash
# 1. Check service health
curl http://localhost:8080/api/auth/public/health

# 2. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# 3. Access protected endpoint with Basic Auth
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

# 4. Logout
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

### 2. User Registration Flow
```bash
# 1. Register new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "roleNames": ["USER"]
  }'

# 2. Login as new user
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","password":"SecurePass123"}'

# 3. Access user endpoints
curl http://localhost:8080/api/user/profile \
  -H "Authorization: Basic am9obl9kb2U6U2VjdXJlUGFzczEyMw=="
```

### 3. Role-Based Access Testing
```bash
# Admin can access all
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

curl http://localhost:8080/api/manager/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

curl http://localhost:8080/api/user/profile \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="

# Manager can access manager and user endpoints
curl http://localhost:8080/api/manager/dashboard \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"

curl http://localhost:8080/api/user/profile \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"

# Manager CANNOT access admin endpoints (403 Forbidden)
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"

# User can only access user endpoints
curl http://localhost:8080/api/user/profile \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"

# User CANNOT access manager endpoints (403 Forbidden)
curl http://localhost:8080/api/manager/dashboard \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

---

## Base64 Encoding for Basic Auth

To encode credentials for Basic Auth:

**Online Tool:** https://www.base64encode.org/

**Command Line:**
```bash
echo -n "admin:admin123" | base64
# Output: YWRtaW46YWRtaW4xMjM=
```

**Python:**
```python
import base64
credentials = base64.b64encode(b"admin:admin123").decode()
print(credentials)
# Output: YWRtaW46YWRtaW4xMjM=
```

---

## API Version
Current Version: **1.0.0**

---

## Last Updated
December 15, 2025

---

## Support
For issues or questions, refer to:
- README.md - Project documentation
- SETUP_GUIDE.md - Setup instructions
- Logs in console - Error details
- H2 Console - Database inspection

