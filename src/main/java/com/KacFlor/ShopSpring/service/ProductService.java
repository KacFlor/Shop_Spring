package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    private final ProductRepository productRepository;

    private final OrderItemRepository orderItemRepository;

    private final OrderRepository orderRepository;

    private final PromotionRepository promotionRepository;

    private final CategoryRepository categoryRepository;

    private final SupplierRepository supplierRepository;

    private final CartRepository cartRepository;

    @Autowired
    public ProductService(CartRepository cartRepository ,SupplierRepository supplierRepository ,CategoryRepository categoryRepository,PromotionRepository promotionRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.promotionRepository = promotionRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.cartRepository = cartRepository;
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public boolean addOrderItem(NewOrderItem newOrderItem, Integer Oid){
        Optional<Order> optionalOrder = orderRepository.findById(Oid);

        if (optionalOrder.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Order not found");
        }

        Order order = optionalOrder.get();
        OrderItem orderItem = new OrderItem(newOrderItem.getName(), newOrderItem.getQuantity(), newOrderItem.getPrice());

        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);

        order.getOrderItems().add(orderItem);
        orderRepository.save(order);

        return true;

    }

    public Product getById(Integer Id){

        Optional<Product> optionalProduct = productRepository.findById(Id);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        return optionalProduct.get();

    }

    public boolean deleteById(Integer Id){

        Optional<Product> optionalProduct = productRepository.findById(Id);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        Product product = optionalProduct.get();

        productRepository.delete(product);

        return true;

    }

    public boolean updateProduct(NewProduct newProduct, Integer Id){
        Optional<Product> optionalProduct = productRepository.findById(Id);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        Product product = optionalProduct.get();
        product.setSku(newProduct.getSku());
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());

        productRepository.save(product);

        return true;

    }

    public boolean addNewProduct(NewProduct newProduct){

        Product product = new Product();

        product.setSku(newProduct.getSku());
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());

        productRepository.save(product);

        return true;

    }

    public boolean addPromotion(Integer PTid, Integer PNid) {

        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Promotion> optionalPromotion = promotionRepository.findById(PNid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalPromotion.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Promotion not found");
        }

        Product product = optionalProduct.get();
        Promotion promotion = optionalPromotion.get();

        product.getPromotions().add(promotion);
        promotion.getProducts().add(product);

        promotionRepository.save(promotion);
        productRepository.save(product);

        return true;

    }

    public boolean removePromotion(Integer PTid, Integer PNid){
        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Promotion> optionalPromotion = promotionRepository.findById(PNid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalPromotion.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Promotion not found");
        }


        Product product = optionalProduct.get();
        Promotion promotion = optionalPromotion.get();

        product.getPromotions().remove(promotion);
        promotion.getProducts().remove(product);

        productRepository.save(product);
        promotionRepository.save(promotion);

        return true;

    }

    public boolean addCategory(Integer PTid, Integer Cid) {

        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Category> optionalCategory = categoryRepository.findById(Cid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalCategory.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Category not found");
        }


        Product product = optionalProduct.get();
        Category category = optionalCategory.get();

        if (product.getCategory() != null) {
            throw new IllegalStateException("Product already has a category assigned.");
        }

        product.setCategory(category);
        category.getProducts().add(product);

        categoryRepository.save(category);
        productRepository.save(product);

        return true;

    }

    public boolean removeCategory(Integer PTid, Integer Cid){
        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Category> optionalCategory = categoryRepository.findById(Cid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalCategory.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Category not found");
        }

        Product product = optionalProduct.get();
        Category category = optionalCategory.get();

        product.setCategory(null);
        category.getProducts().remove(product);

        productRepository.save(product);
        categoryRepository.save(category);

        return true;

    }

    public boolean addSupplier(Integer PTid, Integer Sid) {

        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(Sid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalSupplier.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Supplier not found");
        }


        Product product = optionalProduct.get();
        Supplier supplier = optionalSupplier.get();

        if (product.getSupplier() != null) {
            throw new IllegalStateException("Product already has a supplier assigned.");
        }

        product.setSupplier(supplier);
        supplier.getProducts().add(product);

        supplierRepository.save(supplier);
        productRepository.save(product);

        return true;

    }

    public boolean removeSupplier(Integer PTid, Integer Sid){
        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Supplier> optionalSupplier = supplierRepository.findById(Sid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalSupplier.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Supplier not found");
        }

        Product product = optionalProduct.get();
        Supplier supplier = optionalSupplier.get();

        product.setCategory(null);
        supplier.getProducts().remove(product);

        productRepository.save(product);
        supplierRepository.save(supplier);

        return true;

    }

    public boolean addProductToCart(Integer PTid, Integer Cid) {

        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Cart> optionalCart = cartRepository.findById(Cid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalCart.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Cart not found");
        }

        Product product = optionalProduct.get();
        Cart cart = optionalCart.get();

        cart.getProducts().add(product);

        cartRepository.save(cart);

        return true;

    }

    public boolean removeProductFromCart(Integer PTid, Integer Cid){
        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Cart> optionalCart = cartRepository.findById(Cid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalCart.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Cart not found");
        }


        Product product = optionalProduct.get();
        Cart cart = optionalCart.get();
        if (cart.getProducts().contains(product)) {
            cart.getProducts().remove(product);
            cartRepository.save(cart);
            return true;
        } else {
            throw new ExceptionsConfig.ResourceNotFoundException("Resource not found");
        }

    }
}
