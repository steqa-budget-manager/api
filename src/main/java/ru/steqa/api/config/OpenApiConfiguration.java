package ru.steqa.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Budget Manager API",
                contact = @Contact(
                        name = "Stepan Ivanov",
                        url = "https://github.com/steqa",
                        email = "i.stepan.work@gmail.com"
                ),
                version = "1.0.0"
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        },
        tags = {
                @Tag(name = "Authentication"),
                @Tag(name = "Account"),
                @Tag(name = "Transaction Category"),
                @Tag(name = "Transaction"),
                @Tag(name = "Transaction Template"),
                @Tag(name = "Transfer")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
