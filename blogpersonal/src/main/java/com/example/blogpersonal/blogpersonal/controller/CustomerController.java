package com.example.blogpersonal.blogpersonal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/display_customer")
    public List<Customer> display() {
        return customerService.displayCustomer();
    }

    @GetMapping("/find_customer/{id}")
    public Customer getCustomerById(
        @PathVariable Long id
    ) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/find/{customer_name}")
    public List<Customer> getCustomersByName(
        @PathVariable("customer_name") String name
    ) {
        return customerService.findCustomerByName(name);
    }

    @DeleteMapping("/delete_customer/{idcustomer}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCustomer(
        @PathVariable("idcustomer") Long id 
    ) {
        customerService.deleteCustomer(id);
    }

}
