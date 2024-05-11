package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.controllersRequests.NewReview;
import com.KacFlor.ShopSpring.model.Promotion;
import com.KacFlor.ShopSpring.model.Review;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"reviews"}
)
public class ReviewController{

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Review> getAll(){
        return this.reviewService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getById(@PathVariable("id") Integer id){
        Optional<Review> review = Optional.ofNullable(reviewService.getById(id));
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/customer/{Cid}/product{Pid}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id, @PathVariable("Cid") Integer Cid, @PathVariable("Pid") Integer Pid){
        reviewService.deleteById(id, Cid, Pid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody NewReview newReview, @PathVariable("id") Integer id){
        reviewService.updateReview(newReview, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/new/customer/{Cid}/product{Pid}")
    public ResponseEntity<?> create(@RequestBody NewReview newReview, @PathVariable("Cid") Integer Cid, @PathVariable("Pid") Integer Pid){
        reviewService.addNewReview(newReview, Cid, Pid);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
