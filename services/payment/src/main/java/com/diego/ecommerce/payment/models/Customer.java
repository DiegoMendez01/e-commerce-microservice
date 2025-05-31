package com.diego.ecommerce.payment.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotNull(message = "El nombre es requerido")
        String firstName,
        @NotNull(message = "El apellido es requerido")
        String lastName,
        @NotNull(message = "El correo es requerido")
        @Email(message = "El correo del cliente no esta con el formato")
        String email
) {
}
