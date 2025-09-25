package com.example.blogpersonal.blogpersonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogpersonal.blogpersonal.model.Customer;
import com.example.blogpersonal.blogpersonal.service.CustomerService;

import lombok.Getter;

@Getter
@RequestMapping("/customer")
@RestController
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addcust")
    public Customer addCustomer (
        @RequestBody Customer cust
    ) {
        return customerService.savedCustomer(cust);
    }

}
