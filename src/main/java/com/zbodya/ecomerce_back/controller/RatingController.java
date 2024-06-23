package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Rating;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.request.RatingRequest;
import com.zbodya.ecomerce_back.service.RatingService;
import com.zbodya.ecomerce_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Rating management", description = "Manage ratings")
public class RatingController {

  @Autowired private UserService userService;

  @Autowired private RatingService ratingService;

  @PostMapping("/create")
  @Operation(description = "Create rating")
  public ResponseEntity<Rating> createRating(
      @RequestBody RatingRequest request, @RequestHeader("Authorization") String jwt)
      throws UserException, ProductException {
    User user = userService.findUserProfileByJwt(jwt);
    Rating rating = ratingService.createRating(request, user);
    return  new ResponseEntity<>(rating, HttpStatus.CREATED);
  }

  @GetMapping("/product/{productId}")
  @Operation(description = "Get all ratings for product")
  public ResponseEntity<List<Rating>> getProductRatings(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
    User user = userService.findUserProfileByJwt(jwt);
    List<Rating> productsRatings = ratingService.getProductsRatings(productId);
    return new ResponseEntity<>(productsRatings, HttpStatus.ACCEPTED);
  }

}
