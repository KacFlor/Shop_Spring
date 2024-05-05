package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.PaymentRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Shipment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest{

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    @DisplayName("JUnit test for getAllShipments method")
    @Test
    public void testGetShipmentsAll(){

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "River-town", "Riverdale", "USA", "67890");

        Customer customer = new Customer();

        shipment.setCustomer(customer);
        shipment2.setCustomer(customer);
        shipment3.setCustomer(customer);

        given(shipmentRepository.findAll()).willReturn(List.of(shipment, shipment2, shipment3));

        List<Shipment> result = shipmentService.getAllShipments();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getAddress()).isEqualTo("123 Main Street");
        assertThat(result.get(1).getAddress()).isEqualTo("456 Oak Avenue");
        assertThat(result.get(2).getAddress()).isEqualTo("789 Pine Street");

    }

    @DisplayName("JUnit test for testGetAllCustomerId method")
    @Test
    public void testGetAllByCustomerId(){

        Integer customer1Id = 1;
        Integer customer2Id = 2;

        Shipment shipment1 = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "River-town", "Riverdale", "USA", "67890");

        List<Shipment> shipments = new ArrayList<>();
        shipments.add(shipment2);
        shipments.add(shipment3);

        Customer customer1 = new Customer();
        customer1.setId(customer1Id);
        Customer customer2 = new Customer();
        customer2.setId(customer2Id);

        shipment1.setCustomer(customer1);
        shipment2.setCustomer(customer2);
        shipment3.setCustomer(customer2);

        when(customerRepository.findById(customer2Id)).thenReturn(Optional.of(customer2));
        when(shipmentRepository.findAllByCustomerId(customer2Id)).thenReturn(shipments);

        List<Shipment> result = shipmentService.getAllByCustomerId(customer2Id);
        assertEquals(2, result.size());

        when(customerRepository.findById(customer2Id)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> shipmentService.getAllByCustomerId(customer2Id));

    }

    @DisplayName("JUnit test for testGetById method")
    @Test
    public void testGetById(){

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");

        Integer shipmentId = 1;

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));

        Shipment actualShipment = shipmentService.getById(shipmentId);

        assertEquals(shipment, actualShipment);

        verify(shipmentRepository, times(1)).findById(shipmentId);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> shipmentService.getById(shipmentId));

        verify(shipmentRepository, times(2)).findById(shipmentId);
    }

    @DisplayName("JUnit test for testDeleteAllByCustomerId method")
    @Test
    public void testDeleteAllByCustomerId(){

        Integer customer1Id = 1;
        Integer customer2Id = 2;

        Shipment shipment1 = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "River-town", "Riverdale", "USA", "67890");

        List<Shipment> shipments = new ArrayList<>();
        shipments.add(shipment2);
        shipments.add(shipment3);

        Customer customer1 = new Customer();
        customer1.setId(customer1Id);
        Customer customer2 = new Customer();
        customer2.setId(customer2Id);

        shipment1.setCustomer(customer1);
        shipment2.setCustomer(customer2);
        shipment3.setCustomer(customer2);

        when(customerRepository.findById(customer2Id)).thenReturn(Optional.of(customer2));
        when(shipmentRepository.findAllByCustomerId(customer2Id)).thenReturn(shipments);

        boolean result = shipmentService.deleteAllByCustomerId(customer2Id);

        verify(shipmentRepository, times(1)).deleteAll(List.of(shipment2, shipment3));

        assertTrue(result);

        when(customerRepository.findById(customer2Id)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> shipmentService.getAllByCustomerId(customer2Id));

        verify(customerRepository, times(2)).findById(customer2Id);

    }

    @DisplayName("JUnit test for testUpdateShipment method")
    @Test
    public void testUpdateShipment(){

        Integer shipmentId = 1;
        NewShipment newShipment = new NewShipment(LocalDate.of(2024, 5, 10), "987 Elm Street", "Metropolis", "Metropolis", "USA", "54321");

        Shipment existingShipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        existingShipment.setId(shipmentId);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(existingShipment));

        boolean result = shipmentService.updateShipment(newShipment, shipmentId);
        assertTrue(result);

        assertEquals(newShipment.getShipmentDate(), existingShipment.getShipmentDate());
        assertEquals(newShipment.getAddress(), existingShipment.getAddress());
        assertEquals(newShipment.getCity(), existingShipment.getCity());
        assertEquals(newShipment.getState(), existingShipment.getState());
        assertEquals(newShipment.getCountry(), existingShipment.getCountry());
        assertEquals(newShipment.getZipcode(), existingShipment.getZipcode());

        verify(shipmentRepository, times(1)).findById(shipmentId);
        verify(shipmentRepository, times(1)).save(existingShipment);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> shipmentService.getById(shipmentId));

        verify(shipmentRepository, times(2)).findById(shipmentId);

    }

    @DisplayName("JUnit test for testAddNewPayment method")
    @Test
    public void testAddNewPayment(){

        NewPayment newPayment = new NewPayment(LocalDate.parse("2024-05-04"), "Credit Card", 100.0);
        Integer shipmentId = 1;
        Shipment shipment = new Shipment();
        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));

        boolean result = shipmentService.addNewPayment(newPayment, shipmentId);

        assertTrue(result);
        verify(paymentRepository, times(1)).save(any());
        verify(shipmentRepository, times(1)).save(any());
        assertEquals(newPayment.getPaymentDate(), shipment.getPayment().getPaymentDate());
        assertEquals(newPayment.getPaymentMet(), shipment.getPayment().getPaymentMet());
        assertEquals(newPayment.getAmount(), shipment.getPayment().getAmount());

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> shipmentService.getById(shipmentId));

        verify(shipmentRepository, times(2)).findById(shipmentId);
    }

}
