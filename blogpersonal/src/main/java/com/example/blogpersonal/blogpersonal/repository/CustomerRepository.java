package com.example.blogpersonal.blogpersonal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogpersonal.blogpersonal.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByEmail (String email);
    List<Customer> findAllByName (String name);

}
