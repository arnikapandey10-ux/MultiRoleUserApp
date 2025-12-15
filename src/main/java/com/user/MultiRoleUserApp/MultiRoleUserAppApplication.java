package com.user.MultiRoleUserApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Multi-Role User Authentication System
 *
 * This Spring Boot application provides a complete authentication system with role-based access control.
 *
 * Initial Test Users:
 * - Admin User: username=admin, password=admin123
 * - Manager User: username=manager, password=manager123
 * - Regular User: username=user, password=user123
 *
 * Available Endpoints:
 * - POST /api/auth/login - Login with credentials
 * - POST /api/auth/register - Register new user
 * - GET /api/auth/public/health - Health check
 * - GET /api/admin/dashboard - Admin only
 * - GET /api/manager/dashboard - Manager only
 * - GET /api/user/profile - User access
 *
 * Database: H2 (in-memory)
 * H2 Console: http://localhost:8080/h2-console
 */
@SpringBootApplication
public class MultiRoleUserAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiRoleUserAppApplication.class, args);
	}

}
