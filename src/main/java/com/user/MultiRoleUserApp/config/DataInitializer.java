package com.user.MultiRoleUserApp.config;

import com.user.MultiRoleUserApp.model.Role;
import com.user.MultiRoleUserApp.model.User;
import com.user.MultiRoleUserApp.repository.RoleRepository;
import com.user.MultiRoleUserApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create roles if they don't exist
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() ->
                roleRepository.save(new Role("ADMIN", "System administrator with full access"))
        );

        Role managerRole = roleRepository.findByName("MANAGER").orElseGet(() ->
                roleRepository.save(new Role("MANAGER", "Manager with team management capabilities"))
        );

        Role userRole = roleRepository.findByName("USER").orElseGet(() ->
                roleRepository.save(new Role("USER", "Regular user with basic access"))
        );

        // Create sample users if they don't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    "admin@example.com",
                    "Admin",
                    "User"
            );
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("manager").isEmpty()) {
            User manager = new User(
                    "manager",
                    passwordEncoder.encode("manager123"),
                    "manager@example.com",
                    "Manager",
                    "User"
            );
            manager.getRoles().add(managerRole);
            userRepository.save(manager);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User(
                    "user",
                    passwordEncoder.encode("user123"),
                    "user@example.com",
                    "Regular",
                    "User"
            );
            user.getRoles().add(userRole);
            userRepository.save(user);
        }

        System.out.println("=================================================================");
        System.out.println("Initial data loaded successfully!");
        System.out.println("Test Users Created:");
        System.out.println("1. Admin User: username=admin, password=admin123, role=ADMIN");
        System.out.println("2. Manager User: username=manager, password=manager123, role=MANAGER");
        System.out.println("3. Regular User: username=user, password=user123, role=USER");
        System.out.println("=================================================================");
    }
}



