package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        }else{
            throw new UsernameNotFoundException("Payment not found");
        }
    }

    public Payment getByShipmentId(Integer id){
        Optional<Payment> optionalPayment = paymentRepository.findAll().stream()
                .filter(payment -> payment.getShipment() != null && payment.getShipment().getId().equals(id))
                .findFirst();

        return optionalPayment.orElse(null);
    }

    public boolean updatePayment(NewPayment newPayment, Integer Id){
        Payment payment = paymentRepository.findByShipmentId(Id);

        payment.setPaymentDate(newPayment.getPaymentDate());
        payment.setPaymentMet(newPayment.getPaymentMet());
        payment.setAmount(newPayment.getAmount());

        paymentRepository.save(payment);

        return true;
    }

    public boolean deleteByShipmentId(Integer shipmentId) {
        Optional<Shipment> optionalShipment = shipmentRepository.findById(shipmentId);

        if (optionalShipment.isPresent()) {
            Shipment shipment = optionalShipment.get();
            Payment payment = shipment.getPayment();

            if (payment != null) {
                payment.setShipment(null);
                shipment.setPayment(null);

                paymentRepository.save(payment);
                shipmentRepository.save(shipment);

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
