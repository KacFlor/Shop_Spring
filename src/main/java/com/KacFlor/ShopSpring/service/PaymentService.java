package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService{

    private final PaymentRepository paymentRepository;

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ShipmentRepository shipmentRepository){
        this.paymentRepository = paymentRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public Payment getPaymentById(Integer Id){
        Optional<Payment> optionalPayment = paymentRepository.findById(Id);

        if(optionalPayment.isPresent()){
            return optionalPayment.get();
        }
        else
        {
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
    }

    public Payment getByShipmentId(Integer Id){
        Payment payment = paymentRepository.findByShipmentId(Id);

        if(payment != null && payment.getShipment() != null){
            return payment;
        }
        else
        {
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
    }

    public boolean updatePayment(NewPayment newPayment, Integer Id){
        Payment payment = paymentRepository.findByShipmentId(Id);

        if(payment != null && payment.getShipment() != null){
            payment.setPaymentDate(newPayment.getPaymentDate());
            payment.setPaymentMet(newPayment.getPaymentMet());
            payment.setAmount(newPayment.getAmount());

            paymentRepository.save(payment);

            return true;
        }
        else
        {
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
    }

    public boolean deleteByShipmentId(Integer Id){
        Payment payment = paymentRepository.findByShipmentId(Id);
        Optional<Shipment> shipment = shipmentRepository.findById(Id);

        if(payment != null && payment.getShipment() != null){
            Shipment currentShipment = shipment.get();

            payment.setShipment(null);
            currentShipment.setPayment(null);
            paymentRepository.save(payment);
            shipmentRepository.save(currentShipment);
            return true;

        }
        else
        {
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
    }
}
