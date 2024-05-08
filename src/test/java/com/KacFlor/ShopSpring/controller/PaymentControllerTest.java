package com.KacFlor.ShopSpring.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewPayment;
import com.KacFlor.ShopSpring.model.Payment;
import com.KacFlor.ShopSpring.model.Shipment;
import com.KacFlor.ShopSpring.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetByID() throws Exception{

        Integer paymentId = 1;

        Payment payment = new Payment(LocalDate.of(2024, 5, 3), "Credit Card", 100.0);

        when(paymentService.getPaymentById(paymentId)).thenReturn(payment);

        mockMvc.perform(get("/payment/{id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'paymentDate':'2024-05-03','paymentMet':'Credit Card','amount':100.0}"));


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetShipmentPayment() throws Exception{

        Integer shipmentId = 1;

        Payment payment = new Payment(LocalDate.of(2024, 5, 3), "Credit Card", 100.0);

        Shipment shipment = new Shipment();
        shipment.setId(1);

        when(paymentService.getByShipmentId(shipmentId)).thenReturn(payment);

        mockMvc.perform(get("/payment/shipment/{id}", shipmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'paymentDate':'2024-05-03','paymentMet':'Credit Card','amount':100.0}"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testUpdatePaymentByShipmentId() throws Exception{

        Integer shipmentId = 1;

        Shipment shipment = new Shipment();
        shipment.setId(1);

        LocalDate shipmentDate = LocalDate.of(2024, 5, 10);

        NewPayment newPayment = new NewPayment(shipmentDate, "Credit Card", 100.0);

        String shipmentDateString = shipmentDate.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();
        requestJsonNode.put("paymentDate", shipmentDateString);
        requestJsonNode.put("paymentMet", newPayment.getPaymentMet());
        requestJsonNode.put("amount", newPayment.getAmount());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        when(paymentService.updatePayment(newPayment, shipmentId)).thenReturn(true);

        mockMvc.perform(patch("/payment/shipment/{id}", shipmentId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteByShipmentId() throws Exception{

        Integer shipmentId = 1;
        when(paymentService.deleteByShipmentId(shipmentId)).thenReturn(true);

        mockMvc.perform(delete("/payment/shipment/{id}", shipmentId))
                .andExpect(status().isOk());

    }

}
