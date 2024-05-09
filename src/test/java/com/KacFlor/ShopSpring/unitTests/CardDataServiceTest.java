package com.KacFlor.ShopSpring.unitTests;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.dao.CardDataRepository;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.model.CardData;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.service.CardDataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardDataServiceTest{

    @Mock
    private CardDataRepository cardDataRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CardDataService cardDataService;

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Customer customer = new Customer();
        CardData cardData1 = new CardData("123-123-123-123", customer);
        CardData cardData2 = new CardData("234-234-234-234", customer);
        CardData cardData3 = new CardData("345-345-345-345", customer);

        given(cardDataRepository.findAll()).willReturn(List.of(cardData1, cardData2, cardData3));

        List<CardData> result = cardDataService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getCardNum()).isEqualTo("123-123-123-123");
        assertThat(result.get(1).getCardNum()).isEqualTo("234-234-234-234");
        assertThat(result.get(2).getCardNum()).isEqualTo("345-345-345-345");
    }

    @DisplayName("JUnit test for getCardDataById method")
    @Test
    public void testGetCardDataById(){
        Customer customer = new Customer();
        CardData cardData1 = new CardData("123-123-123-123", customer);
        Integer cardDataId = 1;

        when(cardDataRepository.findById(cardDataId)).thenReturn(Optional.of(cardData1));

        CardData cardData = cardDataService.getCardDataById(cardDataId);

        assertEquals(cardData, cardData1);

        verify(cardDataRepository, times(1)).findById(cardDataId);

        when(cardDataRepository.findById(cardDataId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> cardDataService.getCardDataById(cardDataId));

        verify(cardDataRepository, times(2)).findById(cardDataId);
    }

    @DisplayName("JUnit test for updateData method")
    @Test
    public void testUpdateData(){
        Integer cardDataId = 1;
        Integer customerId = 1;

        NewCardData newCardData = new NewCardData("654-654-654-654");
        Customer customer = new Customer();
        customer.setId(customerId);
        CardData cardData = new CardData("456-456-456-456", customer);
        cardData.setId(cardDataId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(cardDataRepository.findById(cardDataId)).thenReturn(Optional.of(cardData));

        boolean result = cardDataService.updateCardData(newCardData, cardDataId, customerId);
        assertTrue(result);
        assertEquals("654-654-654-654", cardData.getCardNum());

        verify(cardDataRepository, times(1)).save(cardData);

        when(cardDataRepository.findById(cardDataId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> cardDataService.getCardDataById(cardDataId));

        verify(cardDataRepository, times(2)).findById(cardDataId);
    }

    @DisplayName("JUnit test for deleteCardData method")
    @Test
    public void testDeleteCardData(){
        Integer cardDataId = 1;
        Integer customerId = 1;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setCardDatas(new ArrayList<>());
        CardData cardData = new CardData("456-456-456-456", customer);
        cardData.setId(cardDataId);
        customer.getCardDatas().add(cardData);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(cardDataRepository.findById(cardDataId)).thenReturn(Optional.of(cardData));

        boolean result = cardDataService.deleteCardData(cardDataId, customerId);
        assertTrue(result);

        verify(cardDataRepository, times(1)).delete(cardData);

        when(cardDataRepository.findById(cardDataId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> cardDataService.getCardDataById(cardDataId));

        verify(cardDataRepository, times(2)).findById(cardDataId);
    }

}
