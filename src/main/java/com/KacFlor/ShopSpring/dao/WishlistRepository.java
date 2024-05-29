package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{

    Wishlist findByCustomerId(Integer Id);
}
