package com.diego.product.swagger;

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
                        .title("Product Service API")
                        .version("1.0")
                        .description("API para la gestión de productos")
                        .contact(new Contact()
                                .name("Diego Mendez")
                                .email("diegomendez01soft@gmail.com")
                                .url("https://www.linkedin.com/in/diegomendezrojas2002/")
                        )
                ).servers(List.of(
                        new Server().url("http://localhost:8050").description("Servidor Local")
                ));
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch( "/api/v1/categories/**", "/api/v1/products/**")
                .build();
    }
}