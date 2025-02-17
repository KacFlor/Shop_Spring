package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final WishlistRepository wishlistRepository;

    private final CartRepository cartRepository;

    @Autowired
    public ProductService(WishlistRepository wishlistRepository, CartRepository cartRepository ,SupplierRepository supplierRepository ,CategoryRepository categoryRepository,PromotionRepository promotionRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.promotionRepository = promotionRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.cartRepository = cartRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public List<Product> getAll() {
        List<Product> products = productRepository.findAllProducts();
        return products;
    }

    public boolean addOrderItem(NewItem newItem, Integer Pid, Integer Oid) {
        Optional<Order> optionalOrder = orderRepository.findById(Oid);
        Optional<Product> optionalProduct = productRepository.findById(Pid);

        if (optionalOrder.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Order not found");
        }

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        Order order = optionalOrder.get();
        Product product = optionalProduct.get();

        if (product.getOrderItems() == null) {
            product.setOrderItems(new ArrayList<>());
        }

        OrderItem orderItem = new OrderItem(product.getName(), newItem.getQuantity(), product.getPrice());
        orderItem.setProduct(product);

        product.setStock(product.getStock() - orderItem.getQuantity());

        product.getOrderItems().add(orderItem);

        productRepository.save(product);

        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);

        order.getOrderItems().add(orderItem);
        order.setInProgress(false);
        order.setTotalPrice(order.getTotalPrice() + orderItem.getQuantity() * orderItem.getPrice());
        orderRepository.save(order);

        return true;
    }

    public Product setIsBlocked(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        Product product = optionalProduct.get();

        product.setIsBlocked(!product.getIsBlocked());

        productRepository.save(product);

        return product;
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
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());

        productRepository.save(product);

        return true;

    }

    public boolean addNewProduct(NewProduct newProduct){

        Product product = new Product();
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

    public boolean addCategory(Integer productId, Integer categoryId) {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalCategory.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Category not found");
        }

        Product product = optionalProduct.get();
        Category category = optionalCategory.get();

        if (product.getCategory().contains(category)) {
            throw new IllegalStateException("Product is already assigned to this category.");
        }

        product.getCategory().add(category);
        category.getProducts().add(product);

        productRepository.save(product);
        categoryRepository.save(category);

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

        product.getCategory().remove(category);
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

        product.setSupplier(null);
        supplier.getProducts().remove(product);

        productRepository.save(product);
        supplierRepository.save(supplier);

        return true;

    }

    public boolean addProductToCart(NewItem newItem, Integer PTid, Integer Cid) {
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

        if (!cart.getProducts().contains(product)) {
            cart.getProducts().add(product);
            cart.getQuantities().add(newItem.getQuantity().intValue());
        } else {
            int index = cart.getProducts().indexOf(product);
            int existingQuantity = cart.getQuantities().get(index);
            cart.getQuantities().set(index, existingQuantity + newItem.getQuantity().intValue());
        }

        Double totalPrice = 0.0;
        for (int i = 0; i < cart.getProducts().size(); i++) {
            Product p = cart.getProducts().get(i);
            Integer quantity = cart.getQuantities().get(i);
            totalPrice += p.getPrice() * quantity;
        }

        cart.setPrice(totalPrice);

        cartRepository.save(cart);

        return true;
    }

    public boolean removeProductFromCart(NewItem newItem, Integer PTid, Integer Cid) {
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

        if (!cart.getProducts().contains(product)) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not in cart");
        }

        int index = cart.getProducts().indexOf(product);
        int existingQuantity = cart.getQuantities().get(index);

        if (existingQuantity < newItem.getQuantity().intValue()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Not enough product quantity in cart");
        }

        cart.getQuantities().set(index, existingQuantity - newItem.getQuantity().intValue());

        if (cart.getQuantities().get(index) == 0) {
            cart.getProducts().remove(product);
            cart.getQuantities().remove(index);
        }

        Double currentPrice = cart.getPrice() == null ? 0.0 : cart.getPrice();
        double priceChange = newItem.getQuantity() * product.getPrice();
        cart.setPrice(currentPrice - priceChange);

        cartRepository.save(cart);

        return true;
    }

    public boolean removeAllFromCart(Integer Cid) {
        Optional<Cart> optionalCart = cartRepository.findById(Cid);

        if (optionalCart.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Cart not found");
        }

        Cart cart = optionalCart.get();

        cart.getProducts().clear();
        cart.getQuantities().clear();
        cart.setPrice(0.0);

        cartRepository.save(cart);

        return true;
    }

    public boolean addProductToWishlist(Integer productId, Integer wishlistId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ExceptionsConfig.ResourceNotFoundException("Product not found"));
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ExceptionsConfig.ResourceNotFoundException("Wishlist not found"));

        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);

            wishlistRepository.save(wishlist);
            productRepository.save(product);
            return true;
        } else {
            throw new ExceptionsConfig.ResourceNotFoundException("Product already in");
        }
    }


    public boolean removeProductFromWishlist(Integer PTid, Integer Wid){
        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(Wid);

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalWishlist.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Wishlist not found");
        }


        Product product = optionalProduct.get();
        Wishlist wishlist = optionalWishlist.get();
        if (wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
            product.getWishlists().remove(wishlist);
            wishlistRepository.save(wishlist);
            productRepository.save(product);
            return true;
        } else {
            throw new ExceptionsConfig.ResourceNotFoundException("Resource not found");
        }

    }
}
