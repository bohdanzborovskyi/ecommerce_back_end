package com.zbodya.ecomerce_back.repository;

import com.zbodya.ecomerce_back.model.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

  @Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
  List<Rating> getAllProductsRating(@Param("productId") Long productId);
}
