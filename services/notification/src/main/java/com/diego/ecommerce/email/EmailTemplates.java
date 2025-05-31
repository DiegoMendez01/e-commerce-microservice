package com.diego.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Pago procesado exitosamente"),
    ORDER_CONFIRMATION("order-confirmation.html", "Confirmación de pedido")
    ;

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}