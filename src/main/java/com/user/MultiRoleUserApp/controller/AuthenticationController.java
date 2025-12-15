package com.user.MultiRoleUserApp.controller;

import com.user.MultiRoleUserApp.dto.ApiResponse;
import com.user.MultiRoleUserApp.dto.LoginRequest;
import com.user.MultiRoleUserApp.dto.LoginResponse;
import com.user.MultiRoleUserApp.dto.UserRegistrationRequest;
import com.user.MultiRoleUserApp.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Authentication", description = "User authentication and registration endpoints")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Public endpoint to test connectivity
     */
    @GetMapping("/public/health")
    @Operation(
            summary = "Health Check",
            description = "Check if the authentication service is running",
            tags = "Authentication"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Service is running",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(new ApiResponse("Authentication service is running", true));
    }

    /**
     * Register a new user
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register New User",
            description = "Create a new user account with specified roles",
            tags = "Authentication"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or user already exists"
            )
    })
    public ResponseEntity<LoginResponse> register(@RequestBody UserRegistrationRequest request) {
        try {
            LoginResponse response = authenticationService.registerUser(request);
            if (response.isSuccess()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("Registration failed: " + e.getMessage(), false, null, null, null, null));
        }
    }

    /**
     * Login endpoint - authenticates user with username and password
     */
    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Authenticate user with credentials and get user details with roles",
            tags = "Authentication"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials"
            )
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Attempt to authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // If authentication successful, get full user info
            LoginResponse response = authenticationService.authenticateUser(request);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Invalid credentials", false, null, null, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("Login failed: " + e.getMessage(), false, null, null, null, null));
        }
    }

    /**
     * Logout endpoint (just for API consistency)
     */
    @PostMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Terminate user session",
            tags = "Authentication"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Logout successful"
            )
    })
    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.ok(new ApiResponse("Logout successful", true));
    }
}

