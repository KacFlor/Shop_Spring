package com.example.shopSpring.service;

import com.example.shopSpring.model.OrderItem;
import com.example.shopSpring.dao.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getOrderItem() {
        return orderItemRepository.findAll();
    }
}
