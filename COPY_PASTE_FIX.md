# ✅ COPY & PASTE - SWAGGER FIX

Just copy and paste these commands one by one into your terminal.

---

## Command 1: Kill Port 8080
```bash
lsof -ti:8080 | xargs kill -9 2>/dev/null || true
```

---

## Command 2: Navigate to Project
```bash
cd /Users/navinraj/Downloads/MultiRoleUserApp
```

---

## Command 3: Clean Build
```bash
./mvnw clean package -DskipTests
```

**WAIT for this to finish!** You should see: `BUILD SUCCESS`

---

## Command 4: Run Application
```bash
java -jar target/MultiRoleUserApp-0.0.1-SNAPSHOT.jar
```

**WAIT for this message:** `Tomcat started on port(s): 8080`

---

## Command 5: Open Swagger
Copy this URL into your browser:
```
http://localhost:8080/swagger-ui.html
```

---

## ✅ You're Done!

You should now see Swagger UI with:
- API title: "Multi-Role User Authentication API v1.0.0"
- All 13 endpoints listed
- No 401 Unauthorized error

---

## 🧪 Quick Test

1. Find the "Authentication" section
2. Click "GET /api/auth/public/health"
3. Click "Try it out"
4. Click "Execute"
5. You should see: "200" response code with `"success": true`

---

**That's it!** 🎉

