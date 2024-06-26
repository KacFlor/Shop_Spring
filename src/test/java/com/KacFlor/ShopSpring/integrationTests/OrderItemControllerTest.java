package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewItem;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.service.OrderItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext
public class OrderItemControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception {
        OrderItem orderItem1 = new OrderItem("Test1", 222.0, 22.22);
        OrderItem orderItem2 = new OrderItem("Test2", 333.0, 22.22);
        OrderItem orderItem3 = new OrderItem("Test3", 333.0, 22.22);

        when(orderItemService.getAll()).thenReturn(List.of(orderItem1, orderItem2, orderItem3));

        mockMvc.perform(get("/order-items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'name':'Test1','quantity':222.0,'price':22.22},{'name':'Test2','quantity':333.0,'price':22.22},{'name':'Test3','quantity':333.0,'price':22.22}]"));
    }


    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception {
        Integer itemId = 1;

        OrderItem orderItem = new OrderItem("Test1", 22.00, 2.22);
        orderItem.setId(itemId);

        when(orderItemService.getOrderItemById(itemId)).thenReturn(orderItem);

        mockMvc.perform(get("/order-items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'name':'Test1','quantity':22.00,'price':2.22}"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception {
        Integer itemId = 2;
        Integer Oid = 2;
        Integer Pid = 2;

        NewItem newItem = new NewItem(2.00);

        when(orderItemService.deleteById(newItem, itemId, Oid, Pid)).thenReturn(true);

        mockMvc.perform(delete("/order-items/{id}",itemId)
                        .content(new ObjectMapper().writeValueAsString(newItem)).contentType(MediaType.APPLICATION_JSON)
                        .param("Oid", String.valueOf(Oid))
                        .param("Pid", String.valueOf(Pid)))
                .andExpect(status().isOk());
    }


}
