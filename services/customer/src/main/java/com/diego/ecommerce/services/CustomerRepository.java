package com.diego.ecommerce.services;

import com.diego.ecommerce.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    boolean existsByEmail(String email);
}