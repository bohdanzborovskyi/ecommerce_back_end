package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.model.Rating;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.RatingRepository;
import com.zbodya.ecomerce_back.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

  private final RatingRepository ratingRepository;
  private final ProductService productService;

  public RatingServiceImpl(RatingRepository ratingRepository, ProductService productService) {
    this.ratingRepository = ratingRepository;
    this.productService = productService;
  }

  @Override
  public Rating createRating(RatingRequest req, User user) throws ProductException {
    Product product = productService.findProductById(req.getProductId());
    Rating rating = new Rating();
    rating.setProduct(product);
    rating.setRating(rating.getRating());
    rating.setCreatedAt(LocalDateTime.now());
    rating.setUser(user);
    return ratingRepository.save(rating);
  }

  @Override
  public List<Rating> getProductsRatings(Long productId) {
    return ratingRepository.getAllProductsRating(productId);
  }
}
