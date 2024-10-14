package com.data;

import com.data.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerAPITests {
    @Autowired
    TestRestTemplate template;

    @Test
    public void testGetList() {

        Customer[] customers =
                template.getForObject("/customers", Customer[].class);

        assertNotNull(customers);
        assertNotNull(customers[0]);
        assertTrue(customers.length > 0);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = template.getForObject("/customers/1", Customer.class);

        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("Bruce", customer.getName());
    }

    @Test
    public void testPost() {

        Customer customer = new Customer();
        customer.setName("Test");
        customer.setEmail("test@example.com");

        URI location = template.postForLocation("/customers", customer, Customer.class);
        assertNotNull(location);

        customer = template.getForObject(location, Customer.class);
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals("Test", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
    }

    @Test
    public void testPut() {

        String path = "/customers/2";
        String newValue = "NewValue" + Math.random();

        Customer customer = template.getForObject(path, Customer.class );

        customer.setName(newValue);
        template.put(path, customer);

        customer = template.getForObject(path, Customer.class );

        assertEquals(newValue, customer.getName());
    }

    @Test
    public void testDelete() {
        String path = "/customers/2";
        template.delete(path);
        Customer customer = template.getForObject(path, Customer.class);

        assertNull(customer);
    }
}
