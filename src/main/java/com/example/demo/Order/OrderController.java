package com.example.demo.Order;
import java.util.List;

import com.example.demo.Cart.Cart;
import com.example.demo.Cart.CartService;
import com.example.demo.Category.CategoryService;
import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"order"}
)
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrder() {return this.orderService.getOrder();}
}
