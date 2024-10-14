package com.data.service;

import com.data.domain.Customer;
import com.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) { customerRepository.save(customer);}

    public Iterable<Customer> findAllCustomers() { return customerRepository.findAll(); }

    public Optional<Customer> findCustomerById(long id) { return customerRepository.findById(id); }
}
