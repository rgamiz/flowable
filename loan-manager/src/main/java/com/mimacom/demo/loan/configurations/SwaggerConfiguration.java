package com.mimacom.demo.loan.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Looan Manager",
                description = "API REST to handle loan requests",
                contact = @Contact(
                        name = "Roberto Gamiz Sanchez",
                        email = "rgamiz@gamil.com"
                )
        ),
        servers = @Server(url = "http://localhost:8180")
)

public class SwaggerConfiguration {
}
