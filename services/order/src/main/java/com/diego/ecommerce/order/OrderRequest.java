package com.diego.ecommerce.order;

import com.diego.ecommerce.product.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "El monto de la orden debe ser positivo")
        BigDecimal totalAmount,
        @NotNull(message = "Debe especificar el método de pago")
        PaymentMethod paymentMethod,
        @NotNull(message = "El cliente es obligatorio")
        @NotEmpty(message = "El cliente es obligatorio")
        @NotBlank(message = "El cliente es obligatorio")
        String customerId,
        @NotEmpty(message = "Debe comprar al menos un producto")
        List<PurchaseRequest> products
) {
}