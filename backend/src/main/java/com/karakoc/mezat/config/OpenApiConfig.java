package com.karakoc.mezat.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "KARAKOC",
                        email = "emirhankarakoc@yahoo.com"
                ),
                description = "ACIK ARTIRMA",
                title = "MEZAT",
                version = "0.0.1"),
        servers = {
                @Server(
                        description = "CLOUD ENV",
                        url = "https://emirhan-backend.kgzkbi.easypanel.host/"
                ),
                @Server(
                        description = "development ENV",
                        url = "http://localhost:8080"
                )
        }

)
public class OpenApiConfig {
}
