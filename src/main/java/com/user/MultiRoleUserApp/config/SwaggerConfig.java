package com.user.MultiRoleUserApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI Configuration
 * Provides API documentation accessible at: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Multi-Role User Authentication API")
                        .version("1.0.0")
                        .description("REST API for user authentication with role-based access control. " +
                                "Features include user login, registration, and role-based endpoint protection."));
    }
}

