package com.diego.ecommerce;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PaymentApplication implements CommandLineRunner {

    @Autowired
    private Flyway flyway;

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Override
    public void run(String... args) {
        flyway.migrate();
    }
}
