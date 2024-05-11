package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.model.Product;
import com.KacFlor.ShopSpring.service.ProductService;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddOrderItem() throws Exception{
        Integer orderId = 1;
        Integer id = 1;

        NewItem newItem = new NewItem();
        newItem.setQuantity(22.00);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String newItemJason = objectMapper.writeValueAsString(newItem);

        when(productService.addOrderItem(newItem, id, orderId)).thenReturn(true);

        mockMvc.perform(post("/products/{id}/order-item", id ).param("Oid", String.valueOf(orderId)).contentType(MediaType.APPLICATION_JSON).content(newItemJason))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100.00);
        Product product2 = new Product("Test2", "Old Name", "Old description", 29.99, 100.00);
        Product product3 = new Product("Test3", "Old Name", "Old description", 29.99, 100.00);


        when(productService.getAll()).thenReturn(List.of(product1, product2, product3));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'sku':'Test1','name':'Old Name','description':'Old description','price':29.99,'stock':100},{'sku':'Test2','name':'Old Name','description':'Old description','price':29.99,'stock':100},{'sku':'Test3','name':'Old Name','description':'Old description','price':29.99,'stock':100}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception{
        Integer productId = 1;

        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100.00);
        product1.setId(productId);

        when(productService.getById(productId)).thenReturn(product1);

        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'sku':'Test1','name':'Old Name','description':'Old description','price':29.99,'stock':100}"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testDeleteById() throws Exception{
        Integer productId = 2;
        when(productService.deleteById(productId)).thenReturn(true);

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testUpdateProduct() throws Exception {

        NewProduct newProduct = new NewProduct("Test1", "Updated Product", "Updated description", 39.99, 50.00);
        Integer productId = 1;

        when(productService.updateProduct(newProduct, productId)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("sku", newProduct.getSku());
        requestJsonNode.put("name", newProduct.getName());
        requestJsonNode.put("description", newProduct.getDescription());
        requestJsonNode.put("price", newProduct.getPrice());
        requestJsonNode.put("stock", newProduct.getStock());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(patch("/products/{id}", productId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void testCreateNewProduct() throws Exception {

        NewProduct newProduct = new NewProduct("Test1", "New Product", "Description for new product", 19.99, 20.00);

        when(productService.addNewProduct(newProduct)).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJsonNode = objectMapper.createObjectNode();

        requestJsonNode.put("sku", newProduct.getSku());
        requestJsonNode.put("name", newProduct.getName());
        requestJsonNode.put("description", newProduct.getDescription());
        requestJsonNode.put("price", newProduct.getPrice());
        requestJsonNode.put("stock", newProduct.getStock());

        String requestJson = objectMapper.writeValueAsString(requestJsonNode);

        mockMvc.perform(post("/products/new").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddPromotionToProductById() throws Exception {
        Integer productTestId = 1;
        Integer promotionTestId = 1;

        when(productService.addPromotion(productTestId, promotionTestId)).thenReturn(true);

        mockMvc.perform(post("/products/{PTid}/promotion", productTestId).param("PNid", String.valueOf(productTestId)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testRemovePromotionToProductById() throws Exception {
        Integer productTestId = 1;
        Integer promotionTestId = 1;

        when(productService.removePromotion(productTestId, promotionTestId)).thenReturn(true);

        mockMvc.perform(delete("/products/{PTid}/promotion", productTestId).param("PNid", String.valueOf(productTestId)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddCategoryToProductById() throws Exception {
        Integer productTestId = 1;
        Integer categoryId = 1;

        when(productService.addCategory(productTestId, categoryId)).thenReturn(true);

        mockMvc.perform(post("/products/{PTid}/category", productTestId).param("CYid", String.valueOf(categoryId)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testRemoveCategoryToProductById() throws Exception {
        Integer productTestId = 1;
        Integer categoryId = 1;

        when(productService.removeCategory(productTestId, categoryId)).thenReturn(true);

        mockMvc.perform(delete("/products/{PTid}/category", productTestId).param("CYid", String.valueOf(categoryId)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddSupplierToProductById() throws Exception {
        Integer productTestId = 1;
        Integer supplierId = 1;

        when(productService.addSupplier(productTestId, supplierId)).thenReturn(true);

        mockMvc.perform(post("/products/{PTid}/supplier", productTestId).param("Sid", String.valueOf(supplierId)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testRemoveSupplierToProductById() throws Exception {
        Integer productTestId = 1;
        Integer supplierId = 1;

        when(productService.removeSupplier(productTestId, supplierId)).thenReturn(true);

        mockMvc.perform(delete("/products/{PTid}/supplier", productTestId).param("Sid", String.valueOf(supplierId)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddProductToCartById() throws Exception {
        Integer productTestId = 1;
        Integer cartId = 1;

        NewItem newItem = new NewItem();
        newItem.setQuantity(22.00);

        when(productService.addProductToCart(newItem, productTestId, cartId)).thenReturn(true);

        mockMvc.perform(post("/products/{id}/cart", productTestId).param("Cid", String.valueOf(cartId)).content(new ObjectMapper().writeValueAsString(newItem)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testRemoveProductFromCartById() throws Exception {
        Integer productTestId = 1;
        Integer cartId = 1;

        NewItem newItem = new NewItem();
        newItem.setQuantity(22.00);

        when(productService.removeProductFromCart(newItem, productTestId, cartId)).thenReturn(true);

        mockMvc.perform(delete("/products/{id}/cart", productTestId).param("Cid", String.valueOf(cartId)).content(new ObjectMapper().writeValueAsString(newItem)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testAddProductToWishlistById() throws Exception {
        Integer productTestId = 1;
        Integer wishlistId = 1;

        when(productService.addProductToWishlist(productTestId, wishlistId)).thenReturn(true);

        mockMvc.perform(post("/products/{PTid}/wishlist", productTestId).param("Wid", String.valueOf(wishlistId)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testRemoveProductFromWishlistById() throws Exception {
        Integer productTestId = 1;
        Integer wishlistId = 1;

        when(productService.removeProductFromWishlist(productTestId, wishlistId)).thenReturn(true);

        mockMvc.perform(delete("/products/{PTid}/wishlist", productTestId).param("Wid", String.valueOf(wishlistId)))
                .andExpect(status().isOk());

    }

}
