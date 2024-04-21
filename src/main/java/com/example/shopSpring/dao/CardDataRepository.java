package com.example.shopSpring.dao;

import com.example.shopSpring.model.CardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDataRepository extends JpaRepository<CardData, Long> {
}
