package com.zbodya.ecomerce_back.repository;

import com.zbodya.ecomerce_back.model.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT r FROM Review r WHERE r.product.id=:productId")
  public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
