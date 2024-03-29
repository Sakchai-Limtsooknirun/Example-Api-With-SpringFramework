package com.example.demo.Repository;

import com.example.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByFirstName(String firstName);


}