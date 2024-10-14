package com.data.api;

import com.data.domain.Customer;
import com.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class CustomerAPI {

    @Autowired
    CustomerRepository repo;

    @GetMapping("")
    public String health(){
        return "Data Server is running.";
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        Iterable<Customer> customers = repo.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable long id) {
        Optional<Customer> customer = repo.findById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = repo.save(customer);

        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedCustomer.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {

        // check if customer exists
        Optional<Customer> existingCustomerOpt = repo.findById(id);

        if (existingCustomerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // update existing customer
        Customer existingCustomer = existingCustomerOpt.get();
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());

        // save customer
        repo.save(existingCustomer);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        Optional<Customer> existingCustomer = repo.findById(id);
        if (existingCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.delete(existingCustomer.get());
        return ResponseEntity.ok().build();
    }
}
