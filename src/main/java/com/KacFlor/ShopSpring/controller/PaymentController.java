package com.KacFlor.ShopSpring.controller;

import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getById(@PathVariable("id") Integer id){
        Optional<Payment> payment = Optional.ofNullable(paymentService.getPaymentById(id));
        return ResponseEntity.ok(payment);
    }

    //This endpoint give payment body by shipment id assigned to this payment
    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/shipment/{id}")
    public ResponseEntity<Payment> getByShipmentId(@PathVariable("id") Integer id){
        Payment payment = paymentService.getByShipmentId(id);
        return ResponseEntity.ok(payment);
    }

    //This endpoint update payment body which has assigned shipment id in the same way as above
    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/shipment/{id}")
    public ResponseEntity<?> updateByShipmentId(@RequestBody NewPayment updatedPayment, @PathVariable("id") Integer id){
        paymentService.updatePayment(updatedPayment, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //This endpoint delete payment which has assigned shipment id in the same way as above
    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/shipment/{id}")
    public ResponseEntity<?> deleteByShipmentId(@PathVariable("id") Integer id){
        paymentService.deleteByShipmentId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
