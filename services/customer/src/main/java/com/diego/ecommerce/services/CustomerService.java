package com.diego.ecommerce.services;

import com.diego.ecommerce.dto.CustomerRequest;
import com.diego.ecommerce.dto.CustomerResponse;
import com.diego.ecommerce.exceptions.CustomerAlreadyExistsException;
import com.diego.ecommerce.exceptions.CustomerNotFoundException;
import com.diego.ecommerce.mappers.CustomerMapper;
import com.diego.ecommerce.models.Customer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request){
        boolean existsEmail = repository.existsByEmail(request.email());
        if (existsEmail) {
            throw new CustomerAlreadyExistsException(
                    String.format("El cliente con correo %s ya existe", request.email())
            );
        }
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                    format("No se puede actualizar el cliente: no se encontró un cliente con el ID proporcionado: %s", request.id())
                ));

        mergerCustomer(customer, request);
        repository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        boolean exists = repository.findById(customerId).isPresent();
        if (!exists) {
            throw new CustomerNotFoundException(
                    String.format("No se encontró un cliente con el ID proporcionado: %s", customerId)
            );
        }
        return true;
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("No se encontró un cliente con el ID proporcionado: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        var customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("No se encontró un cliente con el ID proporcionado: %s", customerId)
                ));
        repository.delete(customer);
    }
}