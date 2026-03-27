package com.nerdlab.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger configuration.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NerdLab Microservice API")
                        .version("1.0.0")
                        .description("Production-ready Spring Boot microservice template using hexagonal architecture")
                        .contact(new Contact()
                                .name("NerdLab")
                                .url("https://github.com/Andre-Souza539/default-nerdlab-microservice"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
