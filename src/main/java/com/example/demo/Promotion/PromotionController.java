package com.example.demo.Promotion;

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
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"promotion"}
)
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public List<Promotion> getPromotion() {return this.promotionService.getPromotion();}
}
