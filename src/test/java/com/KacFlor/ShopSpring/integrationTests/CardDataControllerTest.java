package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.model.CardData;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.service.CardDataService;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CardDataControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardDataService cardDataService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Customer customer = new Customer();
        CardData cardData1 = new CardData("123-123-123-123", customer);
        CardData cardData2 = new CardData("234-234-234-234", customer);
        CardData cardData3 = new CardData("345-345-345-345", customer);

        when(cardDataService.getAll()).thenReturn(List.of(cardData1, cardData2, cardData3));

        mockMvc.perform(get("/cards-data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'cardNum':'123-123-123-123'},{'cardNum':'234-234-234-234'},{'cardNum':'345-345-345-345'}]"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetById() throws Exception{
        Integer cardDataId = 1;

        Customer customer = new Customer();
        CardData cardData1 = new CardData("123-123-123-123", customer);

        when(cardDataService.getCardDataById(cardDataId)).thenReturn(cardData1);

        mockMvc.perform(get("/cards-data/{id}", cardDataId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'cardNum':'123-123-123-123'}"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDataChangeByCustomerId() throws Exception{
        Integer cardDataId = 1;
        Integer customerId = 1;

        NewCardData newCardData = new NewCardData("456-456-456-456");

        when(cardDataService.updateCardData(newCardData, cardDataId, customerId)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("cardNum", newCardData.getCardNum());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(patch("/cards-data/{CDid}/customer/{Cid}", cardDataId, customerId).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteByCustomerId() throws Exception{
        Integer cardDataId = 1;
        Integer customerId = 1;

        Customer customer = new Customer();
        CardData cardData1 = new CardData("123-123-123-123", customer);

        customer.setId(customerId);
        cardData1.setId(cardDataId);

        when(cardDataService.deleteCardData(cardDataId, customerId)).thenReturn(true);

        mockMvc.perform(delete("/cards-data/{CDid}/customer/{Cid}", cardDataId, customerId))
                .andExpect(status().isOk());
    }



}
