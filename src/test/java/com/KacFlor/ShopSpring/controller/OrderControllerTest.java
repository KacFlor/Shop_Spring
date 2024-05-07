package com.KacFlor.ShopSpring.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.service.OrderService;
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
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAllOrders() throws Exception{
        Order order1 = new Order(LocalDate.of(2024, 5, 3), 2222.22);
        Order order2 = new Order(LocalDate.of(2025, 5, 3), 3333.33);
        Order order3 = new Order(LocalDate.of(2026, 5, 3), 4444.44);

        when(orderService.getAllOrders()).thenReturn(List.of(order1, order2, order3));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'orderDate':'2024-05-03','totalPrice':2222.22},{'orderDate':'2025-05-03','totalPrice':3333.33},{'orderDate':'2026-05-03','totalPrice':4444.44}]"));


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetAllOrderItemsByOrderId() throws Exception{

        Integer orderId = 1;
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("Test1", 2.0, 10.0));
        orderItems.add(new OrderItem("Test2", 3.0, 15.0));

        when(orderService.getAllOrderItems(orderId)).thenReturn(orderItems);

        mockMvc.perform(get("/orders/{id}/order-items", orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'name':'Test1','quantity':2.0,'price':10.0},{'name':'Test2','quantity':3.0,'price':15.0}]"));


        verify(orderService, times(1)).getAllOrderItems(orderId);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetById() throws Exception{
        Integer orderId = 1;

        Order order1 = new Order(LocalDate.of(2024, 5, 3), 2222.22);

        when(orderService.getById(orderId)).thenReturn(order1);

        mockMvc.perform(get("/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'orderDate':'2024-05-03','totalPrice':2222.22}"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testUpdateShipment() throws Exception{
        Integer orderId = 2;
        LocalDate shipmentDate = LocalDate.of(2024, 5, 10);
        NewOrder newOrder = new NewOrder(shipmentDate, 2222.22);

        String shipmentDateString = shipmentDate.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("orderDate", shipmentDateString);
        requestJsonNode.put("totalPrice", newOrder.getTotalPrice());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        when(orderService.updateOrder(newOrder, orderId)).thenReturn(true);

        mockMvc.perform(patch("/orders/{id}", orderId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception{
        Integer orderId = 2;
        when(orderService.deleteById(orderId)).thenReturn(true);

        mockMvc.perform(delete("/orders/{id}", orderId))
                .andExpect(status().isOk());
    }

}
