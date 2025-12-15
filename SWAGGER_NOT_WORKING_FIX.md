# Swagger Not Working - Troubleshooting & Fix Guide

## ✅ Quick Fix (Follow These Steps)

### Option 1: Use the Automated Fix Script (Easiest)

```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
chmod +x fix-swagger.sh
./fix-swagger.sh
```

This script will:
1. ✓ Kill any process on port 8080
2. ✓ Clean the project
3. ✓ Download Swagger dependencies
4. ✓ Build with Swagger support
5. ✓ Start the application
6. ✓ Show you the Swagger URLs

Then open: **http://localhost:8080/swagger-ui.html**

---

### Option 2: Manual Fix (Step by Step)

#### Step 1: Kill Port 8080
```bash
lsof -ti:8080 | xargs kill -9
```

#### Step 2: Navigate to Project
```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
```

#### Step 3: Clean & Build
```bash
./mvnw clean package -DskipTests
```

Wait for it to finish. You should see:
```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXX s
```

#### Step 4: Run Application
```bash
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
```

Wait for startup message:
```
=================================================================
Initial data loaded successfully!
...
Tomcat started on port(s): 8080
```

#### Step 5: Open Swagger
```
http://localhost:8080/swagger-ui.html
```

---

## 🔍 Why Swagger Wasn't Working

The issue was that the application **needed to be rebuilt** to include the Swagger dependencies. When you first created the project, it didn't have Swagger. Then we added:

1. ✅ **Swagger dependencies** in pom.xml
2. ✅ **SwaggerConfig.java** - Configuration class
3. ✅ **Security configuration** - Allow Swagger endpoints
4. ✅ **Application properties** - Swagger settings
5. ✅ **Controller annotations** - Swagger documentation

But the JAR file wasn't rebuilt with these changes. That's why Swagger URLs weren't accessible.

---

## ✅ Verification Checklist

After following the fix, verify:

- [ ] Build completes successfully (no errors)
- [ ] Application starts (see "Initial data loaded" message)
- [ ] No errors about port 8080
- [ ] Open http://localhost:8080/swagger-ui.html
- [ ] See "Multi-Role User Authentication API v1.0.0" title
- [ ] See 4 sections: Authentication, Admin, Manager, User
- [ ] Can click on endpoints to see documentation
- [ ] Can click "Try it out"
- [ ] Can test health endpoint without authentication

---

## 🌐 Swagger URLs (After Fix)

| URL | Purpose |
|-----|---------|
| **http://localhost:8080/swagger-ui.html** | Interactive Swagger UI - START HERE |
| http://localhost:8080/v3/api-docs | OpenAPI JSON specification |
| http://localhost:8080/v3/api-docs.yaml | OpenAPI YAML specification |
| http://localhost:8080/api/auth/public/health | Health check endpoint |

---

## 🧪 Test Swagger

### Test 1: Public Endpoint (No Auth Needed)
1. Open http://localhost:8080/swagger-ui.html
2. Find "Authentication" section
3. Click "GET /api/auth/public/health"
4. Click "Try it out"
5. Click "Execute"
6. Should see 200 response

### Test 2: Protected Endpoint (With Auth)
1. Click "Authorize" button (lock icon, top-right)
2. Enter username: `admin`
3. Enter password: `admin123`
4. Click "Authorize"
5. Find "Admin" section
6. Click "GET /api/admin/dashboard"
7. Click "Try it out" → "Execute"
8. Should see 200 response

### Test 3: Access Denial (Wrong Role)
1. Click "Authorize" again
2. Clear and enter:
   - Username: `user`
   - Password: `user123`
3. Try admin endpoint
4. Should see 403 Forbidden

---

## 🐛 If Still Not Working

### Problem 1: Build Failed
**Solution:**
```bash
# Try fresh build
./mvnw clean compile
# Check for error messages and fix
```

### Problem 2: Port 8080 Still in Use
**Solution:**
```bash
# More aggressive kill
sudo lsof -ti:8080 | xargs sudo kill -9

# Or use different port
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar --server.port=8081
# Then access: http://localhost:8081/swagger-ui.html
```

### Problem 3: Application Won't Start
**Solution:**
Check the error message:
- Missing dependencies? → Run: `./mvnw clean package`
- Java not found? → Install Java 17+
- Database error? → Already fixed in config

### Problem 4: Swagger Page Blank
**Solution:**
- Hard refresh browser: `Ctrl+F5` (Windows/Linux) or `Cmd+Shift+R` (Mac)
- Clear browser cache
- Try different browser (Chrome, Firefox)

---

## 📋 Files That Were Changed

### New Files
- `src/main/java/.../config/SwaggerConfig.java` - Swagger configuration
- `fix-swagger.sh` - Automated fix script
- `SWAGGER_GUIDE.md` - Swagger usage guide

### Modified Files
- `pom.xml` - Added Swagger dependencies
- `application.properties` - Added Swagger config
- `SecurityConfig.java` - Added Swagger endpoint permissions
- `AuthenticationController.java` - Added Swagger annotations
- `AdminController.java` - Added Swagger annotations
- `ManagerController.java` - Added Swagger annotations
- `UserController.java` - Added Swagger annotations

---

## 📚 What's Documented in Swagger

### All 13 Endpoints
- ✅ 4 Authentication endpoints (login, register, logout, health)
- ✅ 3 Admin endpoints (dashboard, users, statistics)
- ✅ 3 Manager endpoints (dashboard, team, reports)
- ✅ 3 User endpoints (profile, dashboard, settings)

### For Each Endpoint
- ✅ Operation summary
- ✅ Full description
- ✅ Request/response schemas
- ✅ HTTP status codes
- ✅ Security requirements
- ✅ Example responses

---

## 🚀 Next Steps After Swagger Works

1. **Explore All Endpoints**
   - Click each endpoint in Swagger UI
   - Read documentation
   - See request/response schemas

2. **Test All Endpoints**
   - Use "Try it out" feature
   - Test with different credentials
   - Verify role-based access

3. **Import into Postman (Optional)**
   - Get JSON: http://localhost:8080/v3/api-docs
   - Postman → Import → Link
   - All endpoints automatically imported

4. **Share with Team**
   - Share Swagger URL with developers
   - Everyone can see and test API
   - No setup needed

---

## 💡 Pro Tips

1. **Use Swagger for Development**
   - Faster than curl for testing
   - See responses immediately
   - Test authentication easily

2. **Keep Swagger Annotations Updated**
   - Update @Operation when endpoint changes
   - Update request/response schema comments
   - Documentation stays in sync

3. **Use with Postman**
   - Import OpenAPI spec for better UI
   - Create test collections
   - Automate testing

4. **Generate Client Code**
   - Use OpenAPI spec to generate clients
   - Multiple languages supported
   - Reduces development time

---

## 📞 Support

If still having issues:

1. **Check Application Logs**
   - Look for error messages
   - Check for missing dependencies
   - Verify port not in use

2. **Read Documentation**
   - README.md - Complete guide
   - SWAGGER_GUIDE.md - Swagger details
   - API_REFERENCE.md - Endpoint reference

3. **Try Alternative Port**
   ```bash
   java -jar target/*.jar --server.port=8081
   ```
   Then visit: http://localhost:8081/swagger-ui.html

---

## ✅ Summary

**Your Swagger issue is due to incomplete rebuild.**

**Solution:** Run either:

**Option A (Automated):**
```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
chmod +x fix-swagger.sh
./fix-swagger.sh
```

**Option B (Manual):**
```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
./mvnw clean package -DskipTests
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
```

**Then open:** http://localhost:8080/swagger-ui.html 🚀

---

**Status:** 🔧 Ready to Fix  
**Difficulty:** Easy  
**Time to Fix:** 2-3 minutes

