package com.user.MultiRoleUserApp.controller;

import com.user.MultiRoleUserApp.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Admin", description = "Administrator-only endpoints")
public class AdminController {

    /**
     * Admin-only endpoint
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Admin Dashboard",
            description = "Get admin dashboard with access to system configuration",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Admin dashboard retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - ADMIN role required"
            )
    })
    public ResponseEntity<ApiResponse> getAdminDashboard() {
        return ResponseEntity.ok(new ApiResponse(
                "Welcome to Admin Dashboard",
                true,
                "You have admin access to system configuration, user management, and audit logs"
        ));
    }

    /**
     * Admin-only endpoint to manage users
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "List All Users",
            description = "Retrieve list of all users in the system",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - ADMIN role required"
            )
    })
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse(
                "Fetching all users",
                true,
                "Admin can view all users in the system"
        ));
    }

    /**
     * Admin-only endpoint to view system statistics
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "System Statistics",
            description = "Get system health and statistics",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Statistics retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - ADMIN role required"
            )
    })
    public ResponseEntity<ApiResponse> getSystemStatistics() {
        return ResponseEntity.ok(new ApiResponse(
                "System Statistics",
                true,
                "Total Users: 0, Active Sessions: 0, System Health: Good"
        ));
    }
}

