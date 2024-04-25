package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

}
