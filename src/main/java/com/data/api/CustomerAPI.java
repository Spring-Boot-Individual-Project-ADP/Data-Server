package com.data.api;

import com.data.domain.Customer;
import com.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Iterator;
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
    public ResponseEntity<?> getCustomer(@PathVariable("id") long id) {
        Optional<Customer> customer = repo.findById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        if(customer.getName().isBlank() || customer.getEmail().isBlank()){
            return ResponseEntity.badRequest().build();
        }

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
    public ResponseEntity<?> putCustomer(
            @RequestBody Customer newCustomer,
            @PathVariable("id") long customerId) {


        if (newCustomer.getId() != customerId || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }


        repo.save(newCustomer);


        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        Optional<Customer> existingCustomer = repo.findById(id);
        if (existingCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.delete(existingCustomer.get());
        return ResponseEntity.ok().build();
    }

    //lookupCustomerByName GET
    @GetMapping("/customers/byname/{username}")
    public ResponseEntity<?> lookupCustomerByNameGet(@PathVariable("username") String username,
                                                     UriComponentsBuilder uri) {
        Iterator<Customer> customers = repo.findAll().iterator();
        while(customers.hasNext()) {
            Customer cust = customers.next();
            if(cust.getName().equalsIgnoreCase(username)) {
                ResponseEntity<?> response = ResponseEntity.ok(cust);
                return response;
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //lookupCustomerByName POST
    @PostMapping("/customers/byname")
    public ResponseEntity<?> lookupCustomerByNamePost(@RequestBody String username, UriComponentsBuilder uri) {
        Iterator<Customer> customers = repo.findAll().iterator();
        while(customers.hasNext()) {
            Customer cust = customers.next();
            if(cust.getName().equals(username)) {
                ResponseEntity<?> response = ResponseEntity.ok(cust);
                return response;
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
