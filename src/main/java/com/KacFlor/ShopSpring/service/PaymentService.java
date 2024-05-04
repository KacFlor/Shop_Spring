package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService{

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }


    public Payment getPaymentById(Integer id){return null;}

    public Payment getByShipmentId(Integer id){return null;}

    public boolean updatePayment(NewPayment newPayment, Integer Id){return true;}

    public boolean deleteByShipmentId(Integer Id){return true;}
}
