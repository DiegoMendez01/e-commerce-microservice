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
                        .title("Order Service API")
                        .version("1.0")
                        .description("API para la gestión de pedidos")
                        .contact(new Contact()
                                .name("Diego Mendez")
                                .email("diegomendez01soft@gmail.com")
                                .url("https://www.linkedin.com/in/diegomendezrojas2002/")
                        )
                ).servers(List.of(
                        new Server().url("http://localhost:8070").description("Servidor Local")
                ));
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/api/v1/orders/**", "/api/v1/order-lines/**")
                .build();
    }
}