package com.example.shopSpring.controller;

import java.util.List;

import com.example.shopSpring.model.OrderItem;
import com.example.shopSpring.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"orderitem"}
)
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItem> getOrderItem() {
        return this.orderItemService.getOrderItem();
    }
}
