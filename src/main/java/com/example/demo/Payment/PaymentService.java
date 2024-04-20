package com.example.demo.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    public PaymentService() {

    }

    public List<Payment> getPayment() {return List;}
}
