package com.data.domain;

import jakarta.persistence.*;

@Entity
@Table(name="CUSTOMERS")
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="CUSTOMER_NAME")
    String name;

    String email;
    String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer(){

    }

    public Customer(long id, String name, String email, String password) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public Customer( String name, String email, String password) {
        this.password = password;
        this.email = email;
        this.name = name;
    }
}
