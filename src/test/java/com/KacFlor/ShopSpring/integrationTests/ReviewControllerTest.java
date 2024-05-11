package com.KacFlor.ShopSpring.integrationTests;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.controllersRequests.NewReview;
import com.KacFlor.ShopSpring.model.Promotion;
import com.KacFlor.ShopSpring.model.Review;
import com.KacFlor.ShopSpring.service.PromotionService;
import com.KacFlor.ShopSpring.service.ReviewService;
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
public class ReviewControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Review review1 = new Review(5,"Very satisfied with the product. Excellent quality and fast delivery." );
        Review review2 = new Review(10,"Product was as described. However, delivery took longer than expected." );
        Review review3 = new Review(0,"Disappointed with the product i have. Exceptions my did not meet." );

        when(reviewService.getAll()).thenReturn(List.of(review1,review2,review3));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'rating': 5 ,'comment':'Very satisfied with the product. Excellent quality and fast delivery.'},{'rating': 10 ,'comment':'Product was as described. However, delivery took longer than expected.'},{'rating': 0 ,'comment':'Disappointed with the product i have. Exceptions my did not meet.'}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception{
        Integer reviewId = 1;

        Review review1 = new Review(5,"Very satisfied with the product. Excellent quality and fast delivery." );

        review1.setId(reviewId);

        when(reviewService.getById(reviewId)).thenReturn(review1);

        mockMvc.perform(get("/reviews/{id}", reviewId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'rating': 5 ,'comment':'Very satisfied with the product. Excellent quality and fast delivery.'}"));

    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testUpdateReview() throws Exception {

        NewReview newReview = new NewReview(5,"Very satisfied with the product. Excellent quality and fast delivery." );
        Integer reviewId = 1;

        when(reviewService.updateReview(newReview, reviewId)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("rating", newReview.getRating());
        requestJsonNode.put("comment", newReview.getComment());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(patch("/reviews/{id}", reviewId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception{
        Integer reviewId = 2;
        Integer Cid = 1;
        Integer Pid = 1;
        when(reviewService.deleteById(reviewId, Cid, Pid)).thenReturn(true);

        mockMvc.perform(delete("/reviews/{id}", reviewId).param("Cid", String.valueOf(Cid)).param("Pid", String.valueOf(Pid)))
                .andExpect(status().isOk());

    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testCreateNewReview() throws Exception {

        Integer Cid = 1;
        Integer Pid = 1;
        NewReview newReview = new NewReview(5,"Very satisfied with the product. Excellent quality and fast delivery." );

        when(reviewService.addNewReview(newReview,Cid, Pid)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("rating", newReview.getRating());
        requestJsonNode.put("comment", newReview.getComment());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(post("/reviews/new").param("Cid", String.valueOf(Cid)).param("Pid", String.valueOf(Pid))
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

}
