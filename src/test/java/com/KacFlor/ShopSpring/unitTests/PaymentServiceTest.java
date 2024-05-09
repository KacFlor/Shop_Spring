package com.KacFlor.ShopSpring.unitTests;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.*;
import com.KacFlor.ShopSpring.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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

        Integer paymentId = 1;
        Optional<Payment> payment = Optional.of(new Payment(LocalDate.of(2024, 5, 3), "Credit Card", 100.0));

        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        Optional<Payment> actualPayment = Optional.ofNullable(paymentService.getPaymentById(paymentId));
        assertNotNull(actualPayment);
        assertEquals(payment, actualPayment);

        verify(paymentRepository, times(1)).findById(paymentId);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> paymentService.getPaymentById(paymentId));

        verify(paymentRepository, times(2)).findById(paymentId);
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

        when(paymentRepository.findByShipmentId(shipmentId)).thenReturn(payment);

        Payment result = paymentService.getByShipmentId(shipmentId);
        assertEquals(result, payment);

        verify(paymentRepository, times(1)).findByShipmentId(shipmentId);

        when(paymentRepository.findByShipmentId(shipmentId)).thenReturn(null);

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> paymentService.getByShipmentId(shipmentId));

        verify(paymentRepository, times(2)).findByShipmentId(shipmentId);
    }

    @DisplayName("JUnit test for updatePayment method")
    @Test
    public void testUpdatePayment(){

        Integer shipmentId = 1;
        NewPayment newPayment = new NewPayment(LocalDate.of(2024, 5, 10), "Credit Card", 100.0);

        Payment existingPayment = new Payment(LocalDate.of(2021, 1, 3), "Credit Card", 140.0);

        Shipment shipment = new Shipment();
        shipment.setId(1);
        existingPayment.setShipment(shipment);

        when(paymentRepository.findByShipmentId(shipmentId)).thenReturn(existingPayment);

        boolean result = paymentService.updatePayment(newPayment, shipmentId);
        assertTrue(result);

        assertEquals(newPayment.getPaymentDate(), existingPayment.getPaymentDate());
        assertEquals(newPayment.getPaymentMet(), existingPayment.getPaymentMet());
        assertEquals(newPayment.getAmount(), existingPayment.getAmount());

        verify(paymentRepository, times(1)).findByShipmentId(shipmentId);
        verify(paymentRepository, times(1)).save(existingPayment);

        when(paymentRepository.findByShipmentId(shipmentId)).thenReturn(null);

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> paymentService.getByShipmentId(shipmentId));

        verify(paymentRepository, times(2)).findByShipmentId(shipmentId);

    }

    @DisplayName("JUnit test for deleteByShipmentId method")
    @Test
    public void testDeleteByShipmentId(){
        Integer shipmentId = 1;

        Shipment shipment = new Shipment();
        shipment.setId(shipmentId);
        Payment payment = new Payment();
        shipment.setPayment(payment);
        payment.setShipment(shipment);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));

        when(paymentRepository.findByShipmentId(shipmentId)).thenReturn(payment);

        boolean result = paymentService.deleteByShipmentId(shipmentId);
        assertTrue(result);
        assertNull(payment.getShipment());

        verify(paymentRepository, times(1)).save(payment);
        verify(shipmentRepository, times(1)).save(shipment);

        when(paymentRepository.findByShipmentId(shipmentId)).thenReturn(null);

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> paymentService.getByShipmentId(shipmentId));

        verify(paymentRepository, times(2)).findByShipmentId(shipmentId);
    }

}
