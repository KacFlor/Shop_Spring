package com.KacFlor.ShopSpring.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.model.Promotion;
import com.KacFlor.ShopSpring.service.PromotionService;
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
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PromotionControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromotionService promotionService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Promotion promotion1 = new Promotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        Promotion promotion2 = new Promotion("Back to School", "Back to school promotion", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 30), 0.15);
        Promotion promotion3 = new Promotion("Holiday Season", "Holiday season sale", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31), 0.25);

        when(promotionService.getAll()).thenReturn(List.of(promotion1,promotion2,promotion3));

        mockMvc.perform(get("/promotions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'name':'Summer Sale','description':'Special discount for summer season','startDate':'2024-06-01','endDate':'2024-08-31','discount':0.2},{'name':'Back to School','description':'Back to school promotion','startDate':'2024-09-01','endDate':'2024-09-30','discount':0.15},{'name':'Holiday Season','description':'Holiday season sale','startDate':'2024-12-01','endDate':'2024-12-31','discount':0.25}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception{
        Integer promotionId = 1;

        Promotion promotion1 = new Promotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);

        promotion1.setId(promotionId);

        when(promotionService.getById(promotionId)).thenReturn(promotion1);

        mockMvc.perform(get("/promotions/{id}", promotionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'name':'Summer Sale','description':'Special discount for summer season','startDate':'2024-06-01','endDate':'2024-08-31','discount':0.2}"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception{
        Integer promotionId = 2;
        when(promotionService.deleteById(promotionId)).thenReturn(true);

        mockMvc.perform(delete("/promotions/{id}", promotionId))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testUpdatePromotion() throws Exception {

        NewPromotion newPromotion = new NewPromotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        Integer promotionId = 1;

        when(promotionService.updatePromotion(newPromotion, promotionId)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("name", newPromotion.getName());
        requestJsonNode.put("description", newPromotion.getDescription());
        requestJsonNode.put("startDate", String.valueOf(newPromotion.getStartDate()));
        requestJsonNode.put("endDate", String.valueOf(newPromotion.getEndDate()));
        requestJsonNode.put("dicount", newPromotion.getDiscount());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(patch("/promotions/{id}", promotionId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testCreateNewPromotion() throws Exception {

        NewPromotion newPromotion = new NewPromotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);

        when(promotionService.addNewPromotion(newPromotion)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("name", newPromotion.getName());
        requestJsonNode.put("description", newPromotion.getDescription());
        requestJsonNode.put("startDate", String.valueOf(newPromotion.getStartDate()));
        requestJsonNode.put("endDate", String.valueOf(newPromotion.getEndDate()));
        requestJsonNode.put("dicount", newPromotion.getDiscount());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(post("/promotions/new")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

}
