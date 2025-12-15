# Testing Checklist & Quick Commands

## Pre-Testing Setup

### 1. Build the Project
```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
./mvnw clean package -DskipTests
```

### 2. Start the Application
```bash
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
```

**Wait for this message:**
```
=================================================================
Initial data loaded successfully!
Test Users Created:
1. Admin User: username=admin, password=admin123, role=ADMIN
2. Manager User: username=manager, password=manager123, role=MANAGER
3. Regular User: username=user, password=user123, role=USER
=================================================================
```

---

## 🧪 Test Cases

### Test 1: Service Health Check ✓
**Purpose:** Verify service is running and accessible

```bash
curl http://localhost:8080/api/auth/public/health
```

**Expected Response:**
```json
{
  "message": "Authentication service is running",
  "success": true
}
```

**Status Code:** 200 OK

---

### Test 2: Admin Login ✓
**Purpose:** Authenticate as admin user

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Expected Response:**
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

**Status Code:** 200 OK

---

### Test 3: Invalid Login ✓
**Purpose:** Test authentication failure

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"wrongpassword"}'
```

**Expected Response:**
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

**Status Code:** 401 Unauthorized

---

### Test 4: Admin Dashboard Access (Success) ✓
**Purpose:** Admin can access admin endpoints

```bash
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Basic Auth Encoding:** `admin:admin123` → `YWRtaW46YWRtaW4xMjM=`

**Expected Response:**
```json
{
  "message": "Welcome to Admin Dashboard",
  "success": true,
  "data": "You have admin access to system configuration, user management, and audit logs"
}
```

**Status Code:** 200 OK

---

### Test 5: Admin Dashboard Access (Denied) ✓
**Purpose:** Regular user cannot access admin endpoints

```bash
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

**Basic Auth Encoding:** `user:user123` → `dXNlcjp1c2VyMTIz`

**Expected Response:**
```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/admin/dashboard"
}
```

**Status Code:** 403 Forbidden

---

### Test 6: Manager Dashboard Access ✓
**Purpose:** Manager can access manager endpoints

```bash
curl http://localhost:8080/api/manager/dashboard \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"
```

**Basic Auth Encoding:** `manager:manager123` → `bWFuYWdlcjptYW5hZ2VyMTIz`

**Expected Response:**
```json
{
  "message": "Welcome to Manager Dashboard",
  "success": true,
  "data": "You have access to team management, reports, and project oversight"
}
```

**Status Code:** 200 OK

---

### Test 7: User Profile Access ✓
**Purpose:** User can access user endpoints

```bash
curl http://localhost:8080/api/user/profile \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

**Basic Auth Encoding:** `user:user123` → `dXNlcjp1c2VyMTIz`

**Expected Response:**
```json
{
  "message": "User Profile",
  "success": true,
  "data": "Username: user, Role: USER"
}
```

**Status Code:** 200 OK

---

### Test 8: User Cannot Access Manager Endpoint ✓
**Purpose:** Verify role hierarchy enforcement

```bash
curl http://localhost:8080/api/manager/dashboard \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

**Expected Response:**
```json
{
  "timestamp": "2025-12-15T22:20:00.000+05:30",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/manager/dashboard"
}
```

**Status Code:** 403 Forbidden

---

### Test 9: Register New User ✓
**Purpose:** Create a new user account

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "test123",
    "email": "testuser@example.com",
    "firstName": "Test",
    "lastName": "User",
    "roleNames": ["USER"]
  }'
```

**Expected Response:**
```json
{
  "message": "User registered successfully",
  "success": true,
  "userId": 4,
  "username": "testuser",
  "email": "testuser@example.com",
  "roles": ["USER"]
}
```

**Status Code:** 201 Created

---

### Test 10: Duplicate User Registration ✓
**Purpose:** Prevent duplicate usernames

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "newpass",
    "email": "newemail@example.com",
    "firstName": "New",
    "lastName": "Admin",
    "roleNames": ["USER"]
  }'
```

**Expected Response:**
```json
{
  "message": "User already exists with username: admin",
  "success": false,
  "userId": null,
  "username": null,
  "email": null,
  "roles": null
}
```

**Status Code:** 400 Bad Request

---

### Test 11: List Users (Admin) ✓
**Purpose:** Admin can view all users

```bash
curl http://localhost:8080/api/admin/users \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Expected Response:**
```json
{
  "message": "Fetching all users",
  "success": true,
  "data": "Admin can view all users in the system"
}
```

**Status Code:** 200 OK

---

### Test 12: System Statistics (Admin) ✓
**Purpose:** View system health

```bash
curl http://localhost:8080/api/admin/statistics \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Expected Response:**
```json
{
  "message": "System Statistics",
  "success": true,
  "data": "Total Users: 3, Active Sessions: 0, System Health: Good"
}
```

**Status Code:** 200 OK

---

### Test 13: Manager Team Members ✓
**Purpose:** Manager can view team

```bash
curl http://localhost:8080/api/manager/team \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"
```

**Expected Response:**
```json
{
  "message": "Team Members",
  "success": true,
  "data": "Manager can view and manage team members"
}
```

**Status Code:** 200 OK

---

### Test 14: Manager Reports ✓
**Purpose:** Manager can access reports

```bash
curl http://localhost:8080/api/manager/reports \
  -H "Authorization: Basic bWFuYWdlcjptYW5hZ2VyMTIz"
```

**Expected Response:**
```json
{
  "message": "Reports",
  "success": true,
  "data": "Manager can access team performance and project reports"
}
```

**Status Code:** 200 OK

---

### Test 15: User Dashboard ✓
**Purpose:** User can view dashboard

```bash
curl http://localhost:8080/api/user/dashboard \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

**Expected Response:**
```json
{
  "message": "Welcome to User Dashboard",
  "success": true,
  "data": "You have access to view your profile and personal settings"
}
```

**Status Code:** 200 OK

---

### Test 16: User Settings ✓
**Purpose:** User can access settings

```bash
curl http://localhost:8080/api/user/settings \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz"
```

**Expected Response:**
```json
{
  "message": "User Settings",
  "success": true,
  "data": "You can update your profile, password, and preferences"
}
```

**Status Code:** 200 OK

---

### Test 17: Logout ✓
**Purpose:** User logout (for API completeness)

```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

**Expected Response:**
```json
{
  "message": "Logout successful",
  "success": true
}
```

**Status Code:** 200 OK

---

## 🗄️ Database Verification

### Access H2 Console
1. URL: http://localhost:8080/h2-console
2. JDBC URL: `jdbc:h2:mem:testdb`
3. Username: `sa`
4. Password: (leave empty)
5. Click "Connect"

### Verify Users Table
```sql
SELECT * FROM users;
```

**Expected Output:**
```
ID | USERNAME | EMAIL | FIRST_NAME | LAST_NAME | ENABLED
1  | admin    | admin@example.com | Admin | User | true
2  | manager  | manager@example.com | Manager | User | true
3  | user     | user@example.com | Regular | User | true
```

### Verify Roles Table
```sql
SELECT * FROM roles;
```

**Expected Output:**
```
ID | NAME | DESCRIPTION
1  | ADMIN | System administrator with full access
2  | MANAGER | Manager with team management capabilities
3  | USER | Regular user with basic access
```

### Verify User-Role Assignments
```sql
SELECT u.username, r.name 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id 
JOIN roles r ON ur.role_id = r.id
ORDER BY u.username;
```

**Expected Output:**
```
USERNAME | NAME
admin | ADMIN
manager | MANAGER
user | USER
```

---

## 📝 Test Automation Script

Save as `test_api.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=========================================="
echo "Testing Multi-Role Auth API"
echo "=========================================="
echo ""

# Test 1: Health Check
echo "Test 1: Health Check"
curl -s "$BASE_URL/api/auth/public/health" | jq '.'
echo ""

# Test 2: Admin Login
echo "Test 2: Admin Login"
curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | jq '.'
echo ""

# Test 3: Admin Dashboard
echo "Test 3: Admin Dashboard Access"
curl -s "$BASE_URL/api/admin/dashboard" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" | jq '.'
echo ""

# Test 4: User Login
echo "Test 4: User Login"
curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"user123"}' | jq '.'
echo ""

# Test 5: User Profile
echo "Test 5: User Profile Access"
curl -s "$BASE_URL/api/user/profile" \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz" | jq '.'
echo ""

# Test 6: User Cannot Access Admin
echo "Test 6: User Cannot Access Admin Dashboard (Should be 403)"
curl -s "$BASE_URL/api/admin/dashboard" \
  -H "Authorization: Basic dXNlcjp1c2VyMTIz" | jq '.'
echo ""

echo "=========================================="
echo "Tests Complete"
echo "=========================================="
```

**Usage:**
```bash
chmod +x test_api.sh
./test_api.sh
```

---

## ✅ Success Criteria

Mark tests as passed when you see:

- [ ] Test 1: Response contains `"success": true`
- [ ] Test 2: Response contains `"userId": 1`
- [ ] Test 3: Response contains `"Welcome to Admin Dashboard"`
- [ ] Test 4: Response contains `"username": "user"`
- [ ] Test 5: Response contains `"Username: user"`
- [ ] Test 6: Response status is 403 Forbidden
- [ ] Test 7-17: All follow similar patterns
- [ ] Database: All tables created with correct data
- [ ] H2 Console: Can connect and view data

---

## 🔍 Debugging Tips

### If Tests Fail:

1. **Check Application Started**
   ```bash
   curl http://localhost:8080/api/auth/public/health
   ```

2. **Check Logs**
   - Look for startup messages
   - Look for "Initial data loaded successfully"
   - Check for errors

3. **Check Port**
   ```bash
   lsof -i :8080
   ```

4. **Check Database**
   - Access H2 Console
   - Verify tables exist
   - Verify data inserted

5. **Test Basic Auth Encoding**
   ```bash
   echo -n "admin:admin123" | base64
   # Should output: YWRtaW46YWRtaW4xMjM=
   ```

6. **Test with Postman**
   - Import endpoints
   - Use Basic Auth type
   - Set correct headers

---

## 📊 Test Coverage Summary

| Category | Tests | Status |
|----------|-------|--------|
| Authentication | 3 | ✓ |
| Authorization | 5 | ✓ |
| CRUD Operations | 4 | ✓ |
| Error Handling | 2 | ✓ |
| Role Hierarchy | 3 | ✓ |
| **Total** | **17** | **✓** |

---

## 🎯 Next Steps After Testing

1. ✅ Verify all tests pass
2. ✅ Check H2 database for data
3. ✅ Review logs for errors
4. ✅ Test with Postman or Insomnia
5. ✅ Create new user and test
6. ✅ Test role hierarchy enforcement
7. ✅ Review code and architecture
8. ✅ Plan enhancements or deployment

---

**Version:** 1.0.0  
**Created:** December 15, 2025  
**Framework:** Spring Boot 4.0  

Enjoy testing! 🚀

