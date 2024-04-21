package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    final private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }
}
