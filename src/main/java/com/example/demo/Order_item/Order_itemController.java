package com.example.demo.Order_item;

import java.util.List;

import com.example.demo.Cart.Cart;
import com.example.demo.Cart.CartService;
import com.example.demo.Category.CategoryService;
import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Order.Order;
import com.example.demo.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"order"}
)
public class Order_itemController {
    private final Order_itemService order_itemService;

    @Autowired
    public Order_itemController(Order_itemService order_itemService) {
        this.order_itemService = order_itemService;
    }

    @GetMapping
    public List<Order_item> getOrder_item() {return this.order_itemService.getOrder_item();}
}
