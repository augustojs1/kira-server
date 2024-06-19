package com.augustodev.kiraserver.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Augusto Souza",
                        email = "augustojsouza1@gmail.com",
                        url = "https://www.linkedin.com/in/augustosouza1"
                ),
                description = "Kira is a project management tool inspired by Jira/Trello where users can create and manage their team, board, tasks and follow up on their project and ticket status. Created using Java 17, Spring Boot 6, Spring Data JPA, MySQL and Docker.",
                title = "Kira Project Management Tool V1",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server(
                        description = "Production ENV",
                        url = "http://localhost:8080/api/v1"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "Use a valid JSON Web Token for authentication",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.DEFAULT
)
public class OpenApiConfig {
}
