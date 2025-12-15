package com.user.MultiRoleUserApp.service;

import com.user.MultiRoleUserApp.dto.LoginRequest;
import com.user.MultiRoleUserApp.dto.LoginResponse;
import com.user.MultiRoleUserApp.dto.UserRegistrationRequest;
import com.user.MultiRoleUserApp.model.Role;
import com.user.MultiRoleUserApp.model.User;
import com.user.MultiRoleUserApp.repository.RoleRepository;
import com.user.MultiRoleUserApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user with roles
     */
    public LoginResponse registerUser(UserRegistrationRequest request) {
        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return new LoginResponse("User already exists with username: " + request.getUsername(), false, null, null, null, null);
        }

        // Create new user
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName()
        );

        // Assign roles
        if (request.getRoleNames() != null && !request.getRoleNames().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : request.getRoleNames()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        User savedUser = userRepository.save(user);

        // Prepare response
        Set<String> roleNames = savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new LoginResponse(
                "User registered successfully",
                true,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                roleNames
        );
    }

    /**
     * Authenticate user with username and password
     */
    public LoginResponse authenticateUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse("Invalid username or password", false, null, null, null, null);
        }

        if (!user.isEnabled()) {
            return new LoginResponse("User account is disabled", false, null, null, null, null);
        }

        if (!user.isAccountNonLocked()) {
            return new LoginResponse("User account is locked", false, null, null, null, null);
        }

        // Prepare response
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new LoginResponse(
                "Login successful",
                true,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roleNames
        );
    }

    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Get user by ID
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * Check if user has role
     */
    public boolean userHasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }

    /**
     * Update user roles
     */
    public void updateUserRoles(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);
    }
}

