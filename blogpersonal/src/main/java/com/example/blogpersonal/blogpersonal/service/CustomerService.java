package com.example.blogpersonal.blogpersonal.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Customer> displayCustomer() {
        return customerRepository.findAll()
                            .stream()
                            // .map(c)  .map is used to convert one object to another object
                            .collect(Collectors.toList());
    }

    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findCustomerByName(name)
                        .stream()
                        .collect(Collectors.toList());
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                        .orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

}
