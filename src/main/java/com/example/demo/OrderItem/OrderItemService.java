package com.example.demo.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    public OrderItemService() {

    }

    public List<OrderItem> getOrderItem() {return List.of();}
}
