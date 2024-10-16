package com.data;

import com.data.api.CustomerAPI;
import com.data.domain.Customer;
import com.data.repository.CustomerRepository;
import com.data.security.AuthFilter;
import com.data.security.JWTHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerAPI.class)
public class CustomerAPITests {

    private static String validToken;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeAll
    public static void setup() {
        validToken = JWTHelper.createToken("com.data.apis");
    }

    @Test
    void getAllCustomersTest() throws Exception {
        // Mock repository response
        when(customerRepository.findAll()).thenReturn(List.of(
                new Customer(1L, "John Doe", "john@example.com", "password"),
                new Customer(2L, "Jane Doe", "jane@example.com", "password")
        ));

        mockMvc.perform(get("/customers").header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        // Mock repository response for a specific customer
        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer(1L, "John Doe", "john@example.com", "password")));

        mockMvc.perform(get("/customers/1").header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void createNewCustomerTest() throws Exception {
        Customer newCustomer = new Customer("John Doe", "john@example.com", "password");

        // Mock the repository's save method to assign an ID
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.setId(1L); // Simulate saving and assigning an ID
            return customer;
        });

        // Perform the POST request
        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newCustomer))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isCreated());

        // Verify customer saved correctly
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void updateCustomerTest() throws Exception {
        Customer existingCustomer = new Customer(1L, "John Doe", "john@example.com", "password");

        // Mock existing customer and save behavior
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer updatedCustomer = invocation.getArgument(0);
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            return existingCustomer;
        });

        // Create an updated customer object
        Customer updatedCustomer = new Customer(1L, "John Doe Updated", "john.updated@example.com", "newpassword");

        // Perform the PUT request
        mockMvc.perform(put("/customers/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatedCustomer))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isOk());

        // Verify customer updated correctly
        assertEquals("John Doe Updated", existingCustomer.getName());
        assertEquals("john.updated@example.com", existingCustomer.getEmail());
    }

    @Test
    void deleteCustomerTest() throws Exception {
        // Mock behavior for existing customer
        when(customerRepository.existsById(1L)).thenReturn(true);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer(1L, "John Doe", "john@example.com", "password")));

        // Perform the DELETE request for an existing customer
        mockMvc.perform(delete("/customers/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isNoContent());

        // Verify the delete method was called
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void deleteNonExistentCustomerTest() throws Exception {
        // Mock behavior for non-existent customer
        when(customerRepository.existsById(2L)).thenReturn(false);

        // Perform the DELETE request for a non-existing customer
        mockMvc.perform(delete("/customers/{id}", 2L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                .andExpect(status().isNotFound());

        // Verify the delete method was never called
        verify(customerRepository, never()).deleteById(2L);
    }




    @Configuration
    static class TestConfig {


        @Bean
        public AuthFilter authFilter() {
            return new AuthFilter(); // Ensure that the AuthFilter is registered as a bean
        }
    }
}


