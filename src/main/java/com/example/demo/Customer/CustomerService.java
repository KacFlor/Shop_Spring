package com.example.demo.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    public CustomerService() {

    }

    public List<Customer> getCustomer() {return List.of();}
}
