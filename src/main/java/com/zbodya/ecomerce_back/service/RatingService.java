package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Rating;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

  public Rating createRating(RatingRequest req, User user) throws ProductException;

  public List<Rating> getProductsRatings(Long productId);
}
