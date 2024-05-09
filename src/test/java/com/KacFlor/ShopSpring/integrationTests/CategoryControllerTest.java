package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewCategory;
import com.KacFlor.ShopSpring.model.Category;
import com.KacFlor.ShopSpring.service.CategoryService;
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
public class CategoryControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Category category1 = new Category("Test1");
        Category category2 = new Category("Test2");
        Category category3 = new Category("Test3");

        when(categoryService.getAll()).thenReturn(List.of(category1, category2, category3));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'name':'Test1'}, {'name':'Test2'}, {'name':'Test3'}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception{
        Integer categoryId = 1;

        Category category1 = new Category("Test1");

        category1.setId(categoryId);

        when(categoryService.getById(categoryId)).thenReturn(category1);

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'name':'Test1'}"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception{
        Integer categoryId = 2;
        when(categoryService.deleteById(categoryId)).thenReturn(true);

        mockMvc.perform(delete("/categories/{id}", categoryId))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testUpdateCategory() throws Exception{

        NewCategory newCategory = new NewCategory("Summer Sale");
        Integer categoryId = 1;

        when(categoryService.updateCategory(newCategory, categoryId)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("name", newCategory.getName());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(patch("/categories/{id}", categoryId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testCreateNewPromotion() throws Exception{

        NewCategory newCategory = new NewCategory("Summer Sale");

        when(categoryService.addNewCategory(newCategory)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("name", newCategory.getName());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(post("/categories/new").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

}
