package com.KacFlor.ShopSpring.unitTests;


import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import com.KacFlor.ShopSpring.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest{

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("JUnit test for addOrderItem method")
    @Test
    public void testAddOrderItem(){
        Integer orderId = 1;

        Order order = new Order();
        order.setId(orderId);
        order.setOrderItems(new ArrayList<>());

        NewOrderItem newOrderItem = new NewOrderItem();
        newOrderItem.setName("Test1");
        newOrderItem.setQuantity(22.00);
        newOrderItem.setPrice(250.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        boolean result = productService.addOrderItem(newOrderItem, orderId);
        assertTrue(result);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
        verify(orderRepository, times(1)).save(any(Order.class));

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.addOrderItem(newOrderItem, orderId));

        verify(orderRepository, times(2)).findById(orderId);
    }

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100);
        Product product2 = new Product("Test2", "Old Name", "Old description", 29.99, 100);
        Product product3 = new Product("Test3", "Old Name", "Old description", 29.99, 100);

        given(productRepository.findAll()).willReturn(List.of(product1, product2, product3));

        List<Product> result = productService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getSku()).isEqualTo("Test1");
        assertThat(result.get(1).getSku()).isEqualTo("Test2");
        assertThat(result.get(2).getSku()).isEqualTo("Test3");
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100);
        Integer productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        Product product = productService.getById(productId);

        assertEquals(product, product1);

        verify(productRepository, times(1)).findById(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.getById(productId));

        verify(productRepository, times(2)).findById(productId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer productId = 1;

        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        boolean result = productService.deleteById(productId);
        assertTrue(result);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product1);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.deleteById(productId));

        verify(productRepository, times(2)).findById(productId);
    }

    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void testUpdateProduct(){

        Integer productId = 1;
        NewProduct newProduct = new NewProduct("Test1", "Updated Product", "Updated description", 39.99, 50);

        Product existingProduct = new Product("OldSKU", "Old Name", "Old description", 29.99, 100);
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        boolean result = productService.updateProduct(newProduct, productId);

        assertTrue(result);
        assertEquals(existingProduct.getSku(), newProduct.getSku());
        assertEquals(existingProduct.getName(), newProduct.getName());
        assertEquals(existingProduct.getDescription(), newProduct.getDescription());
        assertEquals(existingProduct.getPrice(), newProduct.getPrice());
        assertEquals(existingProduct.getStock(), newProduct.getStock());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.updateProduct(newProduct, productId));

        verify(productRepository, times(2)).findById(productId);
    }

    @DisplayName("JUnit test for addNewProduct method")
    @Test
    public void testAddNewProduct(){

        NewProduct newProduct = new NewProduct("Test1", "New Product", "Description for new product", 19.99, 20);

        boolean result = productService.addNewProduct(newProduct);
        assertTrue(result);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddPromotion() {
        Integer productId = 1;
        Integer promotionId = 1;

        Product product = new Product();
        product.setId(productId);
        product.setPromotions(new ArrayList<>());

        Promotion promotion = new Promotion("Test1", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        promotion.setId(promotionId);
        promotion.setProducts(new ArrayList<>());

        product.getPromotions().add(promotion);
        promotion.getProducts().add(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion));

        boolean result = productService.addPromotion(productId, promotionId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(promotionRepository, times(1)).findById(promotionId);
        verify(productRepository, times(1)).save(product);
        verify(promotionRepository, times(1)).save(promotion);

        when(promotionRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.addPromotion(productId, promotionId));

        verify(promotionRepository, times(2)).findById(productId);
    }

    @Test
    void testRemovePromotion() {
        Integer productId = 1;
        Integer promotionId = 1;

        Product product = new Product();
        product.setId(productId);
        product.setPromotions(new ArrayList<>());

        Promotion promotion = new Promotion("Test1", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        promotion.setId(promotionId);
        promotion.setProducts(new ArrayList<>());

        product.getPromotions().add(promotion);
        promotion.getProducts().add(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion));

        boolean result = productService.removePromotion(productId, promotionId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(promotionRepository, times(1)).findById(promotionId);
        verify(productRepository, times(1)).save(product);
        verify(promotionRepository, times(1)).save(promotion);

        when(promotionRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.removePromotion(productId, promotionId));

        verify(promotionRepository, times(2)).findById(productId);
    }

    @Test
    void testAddCategory() {
        Integer productId = 1;
        Integer categoryId = 1;

        Product product = new Product();
        product.setId(productId);

        Category category = new Category("Test1");
        category.setId(categoryId);
        category.setProducts(new ArrayList<>());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        boolean result = productService.addCategory(productId, categoryId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, times(1)).save(product);
        verify(categoryRepository, times(1)).save(category);

        when(categoryRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.addCategory(productId, categoryId));

        verify(categoryRepository, times(2)).findById(productId);
    }

    @Test
    void testRemoveCategory() {
        Integer productId = 1;
        Integer categoryId = 1;

        Product product = new Product();
        product.setId(productId);


        Category category = new Category("Test1");
        category.setId(categoryId);
        category.setProducts(new ArrayList<>());

        product.setCategory(null);
        category.getProducts().add(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        boolean result = productService.removeCategory(productId, categoryId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(productRepository, times(1)).save(product);
        verify(categoryRepository, times(1)).save(category);

        when(categoryRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.removeCategory(productId, categoryId));

        verify(categoryRepository, times(2)).findById(productId);
    }

    @Test
    void testAddSupplier() {
        Integer productId = 1;
        Integer supplierId = 1;

        Product product = new Product();
        product.setId(productId);

        Supplier supplier = new Supplier("Test1");
        supplier.setId(supplierId);
        supplier.setProducts(new ArrayList<>());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        boolean result = productService.addSupplier(productId, supplierId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(supplierRepository, times(1)).findById(supplierId);
        verify(productRepository, times(1)).save(product);
        verify(supplierRepository, times(1)).save(supplier);

        when(supplierRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.addSupplier(productId, supplierId));

        verify(supplierRepository, times(2)).findById(productId);
    }

    @Test
    void testRemoveSupplier() {
        Integer productId = 1;
        Integer supplierId = 1;

        Product product = new Product();
        product.setId(productId);

        Supplier supplier = new Supplier("Test1");
        supplier.setId(supplierId);
        supplier.setProducts(new ArrayList<>());

        product.setSupplier(null);
        supplier.getProducts().add(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        boolean result = productService.removeSupplier(productId, supplierId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(supplierRepository, times(1)).findById(supplierId);
        verify(productRepository, times(1)).save(product);
        verify(supplierRepository, times(1)).save(supplier);

        when(supplierRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.removeSupplier(productId, supplierId));

        verify(supplierRepository, times(2)).findById(productId);
    }

    @Test
    void testAddProductToCart() {
        Integer productId = 1;
        Integer cartId = 1;

        Product product = new Product();
        product.setId(productId);

        Cart cart = new Cart(1);
        cart.setId(cartId);
        cart.setProducts(new ArrayList<>());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        boolean result = productService.addProductToCart(productId, cartId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveProductFromCart() {
        Integer productId = 1;
        Integer cartId = 1;

        Product product = new Product();
        product.setId(productId);

        Cart cart = new Cart(1);
        cart.setId(cartId);
        cart.setProducts(new ArrayList<>());

        product.setCart(cart);
        cart.getProducts().add(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        boolean result = productService.removeProductFromCart(productId, cartId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testAddProductToWishlist() {
        Integer productId = 1;
        Integer wishlistId = 1;

        Product product = new Product();
        product.setId(productId);

        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistId);
        wishlist.setProducts(new ArrayList<>());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        boolean result = productService.addProductToWishlist(productId, wishlistId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(wishlistRepository, times(1)).findById(wishlistId);
        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    void testRemoveProductFromWishlist() {
        Integer productId = 1;
        Integer wishlistId = 1;

        Product product = new Product();
        product.setId(productId);

        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistId);
        wishlist.setProducts(new ArrayList<>());

        product.setWishlist(wishlist);
        wishlist.getProducts().add(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist));

        boolean result = productService.removeProductFromWishlist(productId, wishlistId);

        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(wishlistRepository, times(1)).findById(wishlistId);
        verify(wishlistRepository, times(1)).save(wishlist);
    }


}
