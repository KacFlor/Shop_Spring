package com.KacFlor.ShopSpring.controller;

import java.util.List;

import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"payment"}
)
public class PaymentController{

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getPayment(){
        return this.paymentService.getPayment();
    }
}
