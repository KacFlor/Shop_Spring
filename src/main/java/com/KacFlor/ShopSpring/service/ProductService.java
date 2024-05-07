package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.ProductRepository;
import com.KacFlor.ShopSpring.dao.PromotionRepository;
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

    @Autowired
    public ProductService(PromotionRepository promotionRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.promotionRepository = promotionRepository;
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public boolean addOrderItem(NewOrderItem newOrderItem, Integer Oid){
        Optional<Order> optionalOrder = orderRepository.findById(Oid);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            OrderItem orderItem = new OrderItem(newOrderItem.getName(), newOrderItem.getQuantity(), newOrderItem.getPrice());

            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);

            order.getOrderItems().add(orderItem);
            orderRepository.save(order);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Order not found");
        }
    }

    public Product getById(Integer Id){

        Optional<Product> optionalProduct = productRepository.findById(Id);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }
    }

    public boolean deleteById(Integer Id){

        Optional<Product> optionalProduct = productRepository.findById(Id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();

            productRepository.delete(product);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }
    }

    public boolean updateProduct(NewProduct newProduct, Integer Id){
        Optional<Product> optionalProduct = productRepository.findById(Id);

        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setSku(newProduct.getSku());
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setPrice(newProduct.getPrice());
            product.setStock(newProduct.getStock());

            productRepository.save(product);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
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

        if (optionalProduct.isPresent() && optionalPromotion.isPresent()) {
            Product product = optionalProduct.get();
            Promotion promotion = optionalPromotion.get();

            product.getPromotions().add(promotion);
            promotion.getProducts().add(product);

            promotionRepository.save(promotion);
            productRepository.save(product);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
    }

    public boolean removePromotion(Integer PTid, Integer PNid){
        Optional<Product> optionalProduct = productRepository.findById(PTid);
        Optional<Promotion> optionalPromotion = promotionRepository.findById(PNid);

        if (optionalProduct.isPresent() && optionalPromotion.isPresent()) {
            Product product = optionalProduct.get();
            Promotion promotion = optionalPromotion.get();

            product.getPromotions().remove(promotion);
            promotion.getProducts().remove(product);

            productRepository.save(product);
            promotionRepository.save(promotion);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Payment not found");
        }
    }
}
