package com.user.MultiRoleUserApp.dto;

import java.util.Set;

public class LoginResponse {
    private String message;
    private boolean success;
    private Long userId;
    private String username;
    private String email;
    private Set<String> roles;

    public LoginResponse() {
    }

    public LoginResponse(String message, boolean success, Long userId, String username, String email, Set<String> roles) {
        this.message = message;
        this.success = success;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

