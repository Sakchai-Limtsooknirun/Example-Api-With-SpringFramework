package com.example.demo.Controller;

import com.example.demo.Entity.customer.Customer;
import com.example.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping()
    public List<Customer> getCustomers() {
        return customerService.retrieveCustomer();
    }

    @GetMapping("/{id}")
    //Method GET http://localhost:1150/api/customers/{id}
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.retrieveCustomer(id);
        if(!customer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping(params = "name")
    //Method Get http://localhost:1150/api/customers?name={name}
    public Optional<Customer> getCustomer(@RequestParam(value = "name") String name) {

        return customerService.retrieveCustomer(name);
    }

    @PostMapping()
    //Method POST http://localhost:1150/api/customers with customer payload data
    public ResponseEntity<?> postCustomer(@Valid @RequestBody Customer body) {
        Optional<Customer> customer = customerService.retrieveCustomer(body.getFirstName());
        if(customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else {
            Customer customerCreated = customerService.createCustomer(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
        }
    }

    @PutMapping("/{id}")
    //Method PUT http://localhost:1150/api/customers/{id} with new customer body that will edit
    public ResponseEntity<?> putCustomer(@PathVariable Long id, @Valid @RequestBody Customer body) {
        Optional<Customer> customer = customerService.updateCustomer(id, body);
        if(!customer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if(!customerService.deleteCustomer(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}