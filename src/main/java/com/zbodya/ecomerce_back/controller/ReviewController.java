package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Review;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.request.ReviewRequest;
import com.zbodya.ecomerce_back.service.ReviewService;
import com.zbodya.ecomerce_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review management", description = "Manage reviews")
public class ReviewController {

  @Autowired private ReviewService reviewService;

  @Autowired private UserService userService;

  @PostMapping("/create")
  @Operation(description = "Create review for product")
  public ResponseEntity<Review> createReview(
      @RequestBody ReviewRequest request, @RequestHeader("Authorization") String jwt)
      throws UserException, ProductException {
    User user = userService.findUserProfileByJwt(jwt);
    Review review = reviewService.createReview(request, user);
    return new ResponseEntity<>(review, HttpStatus.CREATED);
  }

  @GetMapping("/product/{productId}")
  @Operation(description = "Get all reviews for product")
  public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId)
      throws ProductException {
    List<Review> prodcutReviews = reviewService.getAllProdcutsReview(productId);
    return new ResponseEntity<>(prodcutReviews, HttpStatus.ACCEPTED);
  }
}
