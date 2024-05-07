package com.KacFlor.ShopSpring.service;
import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest{

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionService promotionService;

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Promotion promotion1 = new Promotion("Test1", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        Promotion promotion2 = new Promotion("Test2", "Back to school promotion", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 30), 0.15);
        Promotion promotion3 = new Promotion("Test3", "Holiday season sale", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31), 0.25);


        given(promotionRepository.findAll()).willReturn(List.of(promotion1,promotion2,promotion3));

        List<Promotion> result = promotionService.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getName()).isEqualTo("Test1");
        assertThat(result.get(1).getName()).isEqualTo("Test2");
        assertThat(result.get(2).getName()).isEqualTo("Test3");
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Promotion promotion1 = new Promotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        Integer promotionId = 1;

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion1));

        Promotion promotion = promotionService.getById(promotionId);

        assertEquals(promotion, promotion1);

        verify(promotionRepository, times(1)).findById(promotionId);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> promotionService.getById(promotionId));

        verify(promotionRepository, times(2)).findById(promotionId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer promotionId = 1;

        Promotion promotion1 = new Promotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion1));

        boolean result = promotionService.deleteById(promotionId);
        assertTrue(result);

        verify(promotionRepository, times(1)).findById(promotionId);
        verify(promotionRepository, times(1)).delete(promotion1);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> promotionService.deleteById(promotionId));

        verify(promotionRepository, times(2)).findById(promotionId);
    }

    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void testUpdateProduct(){

        Integer promotionId = 1;
        NewPromotion newPromotion = new NewPromotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);

        Promotion existingPromotion = new Promotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);
        existingPromotion.setId(promotionId);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(existingPromotion));

        boolean result = promotionService.updatePromotion(newPromotion, promotionId);

        assertTrue(result);
        assertEquals(existingPromotion.getName(), newPromotion.getName());
        assertEquals(existingPromotion.getDescription(), newPromotion.getDescription());
        assertEquals(existingPromotion.getStartDate(), newPromotion.getStartDate());
        assertEquals(existingPromotion.getEndDate(), newPromotion.getEndDate());
        assertEquals(existingPromotion.getDiscount(), newPromotion.getDiscount());

        verify(promotionRepository, times(1)).findById(promotionId);
        verify(promotionRepository, times(1)).save(existingPromotion);

        when(promotionRepository.findById(promotionId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> promotionService.updatePromotion(newPromotion, promotionId));

        verify(promotionRepository, times(2)).findById(promotionId);
    }

    @DisplayName("JUnit test for addNewPromotion method")
    @Test
    public void testAddNewPromotion(){

        NewPromotion newPromotion = new NewPromotion("Summer Sale", "Special discount for summer season", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31), 0.2);

        boolean result = promotionService.addNewPromotion(newPromotion);
        assertTrue(result);

        verify(promotionRepository, times(1)).save(any(Promotion.class));
    }

}
