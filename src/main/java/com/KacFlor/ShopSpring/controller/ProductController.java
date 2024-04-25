package com.KacFlor.ShopSpring.controller;

import java.util.List;

import com.KacFlor.ShopSpring.model.Product;
import com.KacFlor.ShopSpring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"product"}
)
public class ProductController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProduct(){
        return this.productService.getProduct();
    }
}
