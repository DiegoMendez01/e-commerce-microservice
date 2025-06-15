package com.diego.ecommerce.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Configuration
public class CustomCorsFilter {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter corsGlobalFilter() {
        return (exchange, chain) -> {
            String origin = exchange.getRequest().getHeaders().getOrigin();

            if (origin != null && origin.equals("http://localhost:5173")) {
                HttpHeaders headers = exchange.getResponse().getHeaders();

                if (!headers.containsKey("Access-Control-Allow-Origin")) {
                    headers.add("Access-Control-Allow-Origin", origin);
                }
                headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                headers.add("Access-Control-Allow-Headers", "*");
                headers.add("Access-Control-Allow-Credentials", "true");

                if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                    return exchange.getResponse().setComplete();
                }
            }

            return chain.filter(exchange);
        };
    }
}