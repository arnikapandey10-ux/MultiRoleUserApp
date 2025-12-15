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
@RequestMapping("/api/manager")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Manager", description = "Manager-level endpoints (requires MANAGER role or higher)")
public class ManagerController {

    /**
     * Manager-only endpoint
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            summary = "Manager Dashboard",
            description = "Get manager dashboard with team management access",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Manager dashboard retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - MANAGER role required"
            )
    })
    public ResponseEntity<ApiResponse> getManagerDashboard() {
        return ResponseEntity.ok(new ApiResponse(
                "Welcome to Manager Dashboard",
                true,
                "You have access to team management, reports, and project oversight"
        ));
    }

    /**
     * Manager-only endpoint to view team members
     */
    @GetMapping("/team")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            summary = "View Team Members",
            description = "Get list of team members",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Team members retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - MANAGER role required"
            )
    })
    public ResponseEntity<ApiResponse> getTeamMembers() {
        return ResponseEntity.ok(new ApiResponse(
                "Team Members",
                true,
                "Manager can view and manage team members"
        ));
    }

    /**
     * Manager-only endpoint to view reports
     */
    @GetMapping("/reports")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            summary = "View Reports",
            description = "Get team performance and project reports",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Reports retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - MANAGER role required"
            )
    })
    public ResponseEntity<ApiResponse> getReports() {
        return ResponseEntity.ok(new ApiResponse(
                "Reports",
                true,
                "Manager can access team performance and project reports"
        ));
    }
}

