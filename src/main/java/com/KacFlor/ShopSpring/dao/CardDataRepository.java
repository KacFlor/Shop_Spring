package com.KacFlor.ShopSpring.dao;

import com.KacFlor.ShopSpring.model.CardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDataRepository extends JpaRepository<CardData, Long> {
}
