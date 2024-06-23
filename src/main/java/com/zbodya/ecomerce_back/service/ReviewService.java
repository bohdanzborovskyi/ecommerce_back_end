package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Review;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.request.ReviewRequest;
import java.util.List;

public interface ReviewService {

  public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException;

  public List<Review> getAllProdcutsReview(Long productId);
}
