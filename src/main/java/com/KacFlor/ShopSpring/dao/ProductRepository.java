package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    @Query("SELECT p FROM Product p")
    List<Product> findAllProducts();
}
