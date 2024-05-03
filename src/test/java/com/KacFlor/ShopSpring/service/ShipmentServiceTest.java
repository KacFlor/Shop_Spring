package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.Shipment;
import com.KacFlor.ShopSpring.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest{

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    @DisplayName("JUnit test for getAllShipments method")
    @Test
    public void testGetShipmentsAll(){

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "Rivertown", "Riverdale", "USA", "67890");

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

        Integer customerId = 2;

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "Rivertown", "Riverdale", "USA", "67890");

        Customer customer = new Customer();
        customer.setId(3);
        Customer customer1 = new Customer();
        customer1.setId(customerId);

        shipment.setCustomer(customer);
        shipment2.setCustomer(customer1);
        shipment3.setCustomer(customer1);

        given(shipmentRepository.findAll()).willReturn(List.of(shipment, shipment2, shipment3));

        List<Shipment> result = shipmentService.getAllByCustomerId(customerId);

        assertEquals(2, result.size());

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
    }

    @DisplayName("JUnit test for testDeleteAllByCustomerId method")
    @Test
    public void testDeleteAllByCustomerId(){


        Integer customerId = 2;

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "Rivertown", "Riverdale", "USA", "67890");

        Customer customer = new Customer();
        customer.setId(3);
        Customer customer1 = new Customer();
        customer1.setId(customerId);

        shipment.setCustomer(customer);
        shipment2.setCustomer(customer1);
        shipment3.setCustomer(customer1);

        List<Shipment> allShipments = List.of(shipment, shipment2, shipment3);

        when(shipmentRepository.findAll()).thenReturn(allShipments);

        boolean result = shipmentService.deleteAllByCustomerId(customerId);

        verify(shipmentRepository, times(1)).deleteAll(List.of(shipment2, shipment3));

        assertTrue(result);

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

    }

}
