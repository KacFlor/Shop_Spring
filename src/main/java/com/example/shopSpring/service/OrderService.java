package com.example.shopSpring.service;

import com.example.shopSpring.model.Order;
import com.example.shopSpring.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    final private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrder() {
        return orderRepository.findAll();
    }
}
