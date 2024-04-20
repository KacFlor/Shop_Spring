package com.example.demo.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    public OrderService() {

    }

    public List<Order> getOrder() {return List.of();}
}
