package com.data.service;

import com.data.domain.Customer;

import java.util.Optional;

public interface CustomerService {
    public void saveCustomer(Customer customer);
    public Iterable<Customer> findAllCustomers();
    public Optional<Customer> findCustomerById(long id);
}
