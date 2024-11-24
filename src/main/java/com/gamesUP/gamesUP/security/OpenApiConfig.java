package com.gamesUP.gamesUP.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define security scheme (e.g., Basic Auth or Bearer Token)
        SecurityScheme basicAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicAuth", basicAuthScheme)) // Register security scheme
                .addSecurityItem(new SecurityRequirement()
                        .addList("basicAuth")); // Apply security globally
    }
}
