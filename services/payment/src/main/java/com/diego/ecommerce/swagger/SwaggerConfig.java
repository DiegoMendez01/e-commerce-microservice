package com.diego.ecommerce.swagger;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment Service API")
                        .version("1.0")
                        .description("API para la gestión de pagos")
                        .contact(new Contact()
                                .name("Diego Mendez")
                                .email("diegomendez01soft@gmail.com")
                                .url("https://www.linkedin.com/in/diegomendezrojas2002/")
                        )
                ).servers(List.of(
                        new Server().url("http://localhost:8090").description("Servidor Local")
                ));
    }

    @Bean
    public GroupedOpenApi paymentApi() {
        return GroupedOpenApi.builder()
                .group("payments")
                .pathsToMatch( "/api/v1/payments/**")
                .build();
    }
}