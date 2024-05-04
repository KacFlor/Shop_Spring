package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest{

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @DisplayName("JUnit test for getPaymentById method")
    @Test
    public void testGetPaymentById(){

        Payment payment = new Payment(LocalDate.of(2024, 5, 3), "Credit Card", 100.0);

        Integer paymentId = 1;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        Payment actualPayment = paymentService.getPaymentById(paymentId);

        assertEquals(payment, actualPayment);

        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @DisplayName("JUnit test for getByShipmentId method")
    @Test
    public void testGetByShipmentId(){

        Integer shipmentId = 1;

        Payment payment = new Payment(LocalDate.of(2024, 5, 3), "Credit Card", 100.0);
        payment.setId(1);
        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);
        payment.setShipment(shipment);

        List<Payment> payments = List.of(payment);

        when(paymentRepository.findAll()).thenReturn(payments);

        Payment result = paymentService.getByShipmentId(shipmentId);

        assertEquals(shipmentId, result.getShipment().getId());
    }

    @DisplayName("JUnit test for updatePayment method")
    @Test
    public void testUpdatePayment(){

        Integer paymentId = 1;
        NewPayment newPayment = new NewPayment(LocalDate.of(2024, 5, 10), "Credit Card", 100.0);

        Payment existingPayment = new Payment(LocalDate.of(2021, 1, 3), "Credit Card", 140.0);
        existingPayment.setId(paymentId);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));

        boolean result = paymentService.updatePayment(newPayment, paymentId);
        assertTrue(result);

        assertEquals(newPayment.getPaymentDate(), existingPayment.getPaymentDate());
        assertEquals(newPayment.getPaymentMet(), existingPayment.getPaymentMet());
        assertEquals(newPayment.getAmount(), existingPayment.getAmount());

        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, times(1)).save(existingPayment);


    }

    @DisplayName("JUnit test for deleteByShipmentId method")
    @Test
    public void testDeleteByShipmentId(){
        Integer shipmentId = 1;

        Shipment shipmentWithPayment = new Shipment();
        shipmentWithPayment.setId(shipmentId);
        Payment payment = new Payment();
        shipmentWithPayment.setPayment(payment);

        when(shipmentRepository.findById(shipmentId))
                .thenReturn(Optional.of(shipmentWithPayment));

        boolean result = paymentService.deleteByShipmentId(shipmentId);
        assertTrue(result);
    }


}
