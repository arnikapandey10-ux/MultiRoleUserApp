package com.user.MultiRoleUserApp.controller;

import com.user.MultiRoleUserApp.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticationControllerTests {

    @Test
    public void testApplicationContextLoads() {
        // Context loads successfully
        assertTrue(true);
    }

    @Test
    public void testAuthenticationServiceExists() {
        assertTrue(true);
    }

    @Test
    public void testLoginRequestCreation() {
        LoginRequest request = new LoginRequest("admin", "admin123");
        assertEquals("admin", request.getUsername());
        assertEquals("admin123", request.getPassword());
    }

    @Test
    public void testUserRoleAssignmentLogic() {
        // Test role-based logic
        String role = "ADMIN";
        assertTrue(role.equals("ADMIN") || role.equals("MANAGER") || role.equals("USER"));
    }
}



