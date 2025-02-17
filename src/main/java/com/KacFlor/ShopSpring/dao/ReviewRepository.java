package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    Optional<Review> findByCustomerIdAndProductId(Integer customerId, Integer productId);

    List<Review> findByProductId(Integer productId);
}
