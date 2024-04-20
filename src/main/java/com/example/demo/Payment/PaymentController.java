package com.example.demo.Payment;

import java.util.List;

import com.example.demo.Cart.Cart;
import com.example.demo.Cart.CartService;
import com.example.demo.Category.CategoryService;
import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Order.Order;
import com.example.demo.Order.OrderService;
import com.example.demo.Order_item.Order_item;
import com.example.demo.Order_item.Order_itemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"order"}
)
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getPayment() {return this.paymentService.getPayment();}
}
