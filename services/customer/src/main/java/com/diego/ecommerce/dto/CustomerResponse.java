package com.diego.ecommerce.dto;

import com.diego.ecommerce.models.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}