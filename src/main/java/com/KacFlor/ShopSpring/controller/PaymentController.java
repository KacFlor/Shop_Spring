package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.Shipment;
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

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/shipment/{id}")
    public ResponseEntity<Payment> getShipmentPayment(@PathVariable("id") Integer id){
        Payment payment = paymentService.getByShipmentId(id);
        return ResponseEntity.ok(payment);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/shipment/{id}")
    public ResponseEntity<?> updatePaymentByShipmentId(@RequestBody NewPayment newPayment, @PathVariable("id") Integer id){
        paymentService.updatePayment(newPayment, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/shipment/{id}")
    public ResponseEntity deleteByShipmentId(@PathVariable("id") Integer id){
        paymentService.deleteByShipmentId(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
