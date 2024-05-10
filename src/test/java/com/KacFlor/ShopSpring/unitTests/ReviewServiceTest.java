package com.KacFlor.ShopSpring.unitTests;
import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.controllersRequests.NewReview;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import com.KacFlor.ShopSpring.service.ReviewService;
import org.checkerframework.checker.units.qual.C;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest{

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewService reviewService;

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Review review1 = new Review(5,"Very satisfied with the product. Excellent quality and fast delivery." );
        Review review2 = new Review(10,"Product was as described. However, delivery took longer than expected." );
        Review review3 = new Review(0,"Disappointed with the product i have. Exceptions my did not meet." );


        given(reviewRepository.findAll()).willReturn(List.of(review1,review2,review3));

        List<Review> result = reviewService.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getRating()).isEqualTo(5);
        assertThat(result.get(1).getRating()).isEqualTo(10);
        assertThat(result.get(2).getRating()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Review review1 = new Review(5,"Very satisfied with the product. Excellent quality and fast delivery." );
        Integer reviewId = 1;

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review1));

        Review review = reviewService.getById(reviewId);

        assertEquals(review, review1);

        verify(reviewRepository, times(1)).findById(reviewId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.getById(reviewId));

        verify(reviewRepository, times(2)).findById(reviewId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer reviewId = 1;
        Integer customerId = 1;
        Integer productId = 1;

        Review review1 = new Review(5,"Very satisfied with the product. Excellent quality and fast delivery." );

        Customer customer1 = new Customer();
        Product product1 = new Product();

        customer1.setReviews(new ArrayList<>());
        product1.setReviews(new ArrayList<>());

        review1.setCustomer(customer1);
        review1.setProduct(product1);

        customer1.getReviews().add(review1);
        product1.getReviews().add(review1);

        review1.setId(reviewId);
        customer1.setId(customerId);
        product1.setId(productId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review1));
        when(customerRepository.findById(reviewId)).thenReturn(Optional.of(customer1));
        when(productRepository.findById(reviewId)).thenReturn(Optional.of(product1));

        boolean result = reviewService.deleteById(reviewId,customerId,productId);
        assertTrue(result);

        verify(reviewRepository, times(1)).findById(reviewId);
        verify(customerRepository, times(1)).findById(reviewId);
        verify(productRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).delete(review1);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.deleteById(reviewId,customerId,productId));

        verify(reviewRepository, times(2)).findById(reviewId);

        when(customerRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.deleteById(reviewId,customerId,productId));

        verify(customerRepository, times(3)).findById(reviewId);

        when(productRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.deleteById(reviewId,customerId,productId));

        verify(productRepository, times(4)).findById(reviewId);
    }

    @DisplayName("JUnit test for updateReview method")
    @Test
    public void testUpdateReview(){

        Integer reviewId = 1;
        NewReview newReview = new NewReview(5,"Very satisfied with the product. Excellent quality and fast delivery." );

        Review existingReview = new Review(0,"Disappointed with the product i have. Exceptions my did not meet." );
        existingReview.setId(reviewId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));

        boolean result = reviewService.updateReview(newReview, reviewId);

        assertTrue(result);
        assertEquals(existingReview.getRating(), newReview.getRating());
        assertEquals(existingReview.getComment(), newReview.getComment());

        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(existingReview);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.updateReview(newReview, reviewId));

        verify(reviewRepository, times(2)).findById(reviewId);
    }

    @DisplayName("JUnit test for addNewPromotion method")
    @Test
    public void testAddNewPromotion(){

        Integer customerId = 1;
        Integer productId = 1;

        NewReview newReview = new NewReview(5,"Very satisfied with the product. Excellent quality and fast delivery." );
        Customer customer1 = new Customer();
        Product product1 = new Product();

        customer1.setId(customerId);
        product1.setId(productId);

        customer1.setReviews(new ArrayList<>());
        product1.setReviews(new ArrayList<>());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer1));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        boolean result = reviewService.addNewReview(newReview,customerId,productId);
        assertTrue(result);

        verify(reviewRepository, times(2)).save(any(Review.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(productRepository, times(1)).save(any(Product.class));

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.addNewReview(newReview, customerId,productId));

        verify(customerRepository, times(2)).findById(customerId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> reviewService.addNewReview(newReview, customerId,productId));

        verify(productRepository, times(3)).findById(productId);
    }

}
