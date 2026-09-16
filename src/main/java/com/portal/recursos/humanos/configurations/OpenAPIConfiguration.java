package com.portal.recursos.humanos.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Portal de Recursos Humanos API",
                version = "1.0",
                description = "API REST para gerenciamento de funcionários, registros de ponto e relatórios de horas.",
                contact = @Contact(
                        name = "Rodrigo")),
        security = {@SecurityRequirement(name = "oauth2")})
@SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:8080/oauth2/authorize",
                        tokenUrl = "http://localhost:8080/oauth2/token",
                        scopes = {@OAuthScope(
                                name = "openid",
                                description = "Permite autenticação OpenID Connect"),
                                @OAuthScope(name = "profile",
                                        description = "Permite acesso às informações básicas do perfil")})))
public class OpenAPIConfiguration {
}
