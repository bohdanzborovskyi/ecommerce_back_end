package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.model.Review;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.ReviewRepository;
import com.zbodya.ecomerce_back.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }


    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product = productService.findProductById(reviewRequest.getProductId());
        Review review = new Review();
        review.setReview(reviewRequest.getReview());
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllProdcutsReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
