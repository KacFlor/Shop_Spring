package com.example.shopSpring.service;

import com.example.shopSpring.model.Payment;
import com.example.shopSpring.dao.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getPayment() {
        return paymentRepository.findAll();
    }
}
