package com.KacFlor.ShopSpring.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Shipment;
import com.KacFlor.ShopSpring.service.ShipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ShipmentControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAllShipments() throws Exception{

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "River-town", "Riverdale", "USA", "67890");

        when(shipmentService.getAllShipments()).thenReturn(List.of(shipment, shipment2, shipment3));

        mockMvc.perform(get("/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'address':'123 Main Street'},{'address':'456 Oak Avenue'},{'address':'789 Pine Street'}]"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetCustomerShipments() throws Exception{

        Integer customerId = 2;

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");
        Shipment shipment2 = new Shipment(LocalDate.of(2024, 5, 5), "456 Oak Avenue", "Gotham", "Gotham City", "USA", "54321");
        Shipment shipment3 = new Shipment(LocalDate.of(2024, 5, 7), "789 Pine Street", "River-town", "Riverdale", "USA", "67890");

        Customer customer = new Customer();
        customer.setId(customerId);

        shipment.setCustomer(customer);
        shipment2.setCustomer(customer);
        shipment3.setCustomer(customer);

        when(shipmentService.getAllByCustomerId(customerId)).thenReturn(List.of(shipment, shipment2, shipment3));

        mockMvc.perform(get("/shipments/customer/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'address':'123 Main Street'},{'address':'456 Oak Avenue'},{'address':'789 Pine Street'}]"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetShipment() throws Exception{

        Integer shipmentId = 1;

        Shipment shipment = new Shipment(LocalDate.of(2024, 5, 3), "123 Main Street", "Springfield", "Ohio", "USA", "12345");


        when(shipmentService.getById(shipmentId)).thenReturn(shipment);

        mockMvc.perform(get("/shipments/{id}", shipmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'address':'123 Main Street'}"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteAllByCustomerId() throws Exception{

        Integer customerId = 2;
        when(shipmentService.deleteAllByCustomerId(customerId)).thenReturn(true);

        mockMvc.perform(delete("/shipments/customer/{id}", customerId))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception{

        Integer shipmentId = 2;
        when(shipmentService.deleteAllByCustomerId(shipmentId)).thenReturn(true);

        mockMvc.perform(delete("/shipments/customer/{id}", shipmentId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testCreateOrder() throws Exception{

        Integer shipmentId = 1;
        NewOrder newOrder = new NewOrder();
        newOrder.setOrderDate(LocalDate.of(2024, 5, 1));
        newOrder.setTotalPrice(99.99);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String newOrderJson = objectMapper.writeValueAsString(newOrder);

        when(shipmentService.createOrder(newOrder, shipmentId)).thenReturn(true);

        mockMvc.perform(post("/shipments/{id}/order",shipmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newOrderJson))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testUpdateShipment() throws Exception{
        Integer shipmentId = 2;
        LocalDate shipmentDate = LocalDate.of(2024, 5, 10);
        NewShipment newShipment = new NewShipment(shipmentDate, "987 Elm Street", "Metropolis", "Metropolis", "USA", "54321");

        String shipmentDateString = shipmentDate.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();
        requestJsonNode.put("shipmentDate", shipmentDateString);
        requestJsonNode.put("address", newShipment.getAddress());
        requestJsonNode.put("city", newShipment.getCity());
        requestJsonNode.put("state", newShipment.getState());
        requestJsonNode.put("country", newShipment.getCountry());
        requestJsonNode.put("zipcode", newShipment.getZipcode());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        when(shipmentService.updateShipment(newShipment, shipmentId)).thenReturn(true);

        mockMvc.perform(patch("/shipments/{id}", shipmentId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddNewPayment() throws Exception{

        Integer shipmentId = 1;
        NewPayment newPayment = new NewPayment(LocalDate.parse("2024-05-04"), "Credit Card", 100.0);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();
        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        when(shipmentService.addNewPayment(newPayment, shipmentId)).thenReturn(true);

        mockMvc.perform(post("/shipments/{id}/payment", shipmentId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}

