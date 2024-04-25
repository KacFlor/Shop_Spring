package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService{

    final private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrder(){
        return orderRepository.findAll();
    }
}
