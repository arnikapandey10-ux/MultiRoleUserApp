package com.user.MultiRoleUserApp.controller;

import com.user.MultiRoleUserApp.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    /**
     * User-accessible endpoint for their profile
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getUserProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(new ApiResponse(
                "User Profile",
                true,
                "Username: " + username + ", Role: USER"
        ));
    }

    /**
     * User-accessible endpoint for their dashboard
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getUserDashboard() {
        return ResponseEntity.ok(new ApiResponse(
                "Welcome to User Dashboard",
                true,
                "You have access to view your profile and personal settings"
        ));
    }

    /**
     * User-accessible endpoint for settings
     */
    @GetMapping("/settings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getUserSettings() {
        return ResponseEntity.ok(new ApiResponse(
                "User Settings",
                true,
                "You can update your profile, password, and preferences"
        ));
    }
}

