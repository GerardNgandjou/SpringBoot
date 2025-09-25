package com.example.blogpersonal.blogpersonal.service;

import org.springframework.stereotype.Service;

import com.example.blogpersonal.blogpersonal.model.Customer;
import com.example.blogpersonal.blogpersonal.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer savedCustomer (Customer customer) {
        var cust = customerRepository.save(customer);
        return cust;
    }

}
