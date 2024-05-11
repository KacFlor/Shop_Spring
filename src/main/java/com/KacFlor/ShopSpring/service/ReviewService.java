package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewReview;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.ProductRepository;
import com.KacFlor.ShopSpring.dao.ReviewRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Product;
import com.KacFlor.ShopSpring.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService{

    private final ReviewRepository reviewRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository, ProductRepository productRepository){
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    public Review getById(Integer Id){

        Optional<Review> optionalReview = reviewRepository.findById(Id);

        if (optionalReview.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Review not found");
        }

        return optionalReview.get();

    }

    public boolean deleteById(Integer Id, Integer Cid, Integer Pid){
        Optional<Review> optionalReview = reviewRepository.findById(Id);
        Optional<Customer> optionalCustomer = customerRepository.findById(Cid);
        Optional<Product> optionalProduct = productRepository.findById(Pid);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        if (optionalReview.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Review not found");
        }

        Review review = optionalReview.get();
        Customer customer = optionalCustomer.get();
        Product product = optionalProduct.get();

        customer.getReviews().remove(review);
        product.getReviews().remove(review);

        reviewRepository.delete(review);
        customerRepository.save(customer);
        productRepository.save(product);

        return true;

    }

    public boolean updateReview(NewReview newReview, Integer Id){
        Optional<Review> optionalReview = reviewRepository.findById(Id);

        if (optionalReview.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Review not found");
        }

        Review review = optionalReview.get();
        review.setComment(newReview.getComment());
        review.setRating(newReview.getRating());

        reviewRepository.save(review);

        return true;

    }

    public boolean addNewReview(NewReview newReview, Integer Cid, Integer Pid){
        Optional<Customer> optionalCustomer = customerRepository.findById(Cid);
        Optional<Product> optionalProduct = productRepository.findById(Pid);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        Customer customer = optionalCustomer.get();
        Product product = optionalProduct.get();
        Review review = new Review();
        reviewRepository.save(review);
        product.getReviews().add(review);
        customer.getReviews().add(review);

        review.setComment(newReview.getComment());
        review.setRating(newReview.getRating());
        review.setCustomer(customer);
        review.setProduct(product);

        customerRepository.save(customer);
        productRepository.save(product);
        reviewRepository.save(review);

        return true;

    }
}
