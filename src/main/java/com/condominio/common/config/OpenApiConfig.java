package com.condominio.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI condominioOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Sistema de Gestão de Condomínio — API")
                .description("REST API para gestão condominial: moradores, financeiro, reservas, manutenção, visitantes e documentos")
                .version("1.0.0")
                .contact(new Contact().name("Administração").email("admin@condominio.com"))
                .license(new License().name("Proprietário")));
    }
}
