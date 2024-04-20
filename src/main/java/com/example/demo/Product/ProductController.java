package com.example.demo.Product;

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
import com.example.demo.Payment.Payment;
import com.example.demo.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"product"}
)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProduct() {return this.productService.getProduct();}
}
