package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product management", description = "Finding products by category and id")
public class ProductController {

  @Autowired private ProductService productService;

  @GetMapping("/")
  @Operation(description = "Find products by category")
  public ResponseEntity<Page<Product>> findProductByCategory(
      @RequestParam String category,
      @RequestParam List<String> color,
      @RequestParam List<String> size,
      @RequestParam Integer minPrice,
      @RequestParam Integer maxPrice,
      @RequestParam Integer minDiscount,
      @RequestParam String sort,
      @RequestParam String stock,
      @RequestParam Integer pageNumber,
      @RequestParam Integer pageSize) {
    Page<Product> products =
        productService.getAllProduct(
            category,
            color,
            size,
            minPrice,
            maxPrice,
            minDiscount,
            sort,
            stock,
            pageNumber,
            pageSize);
    return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
  }

  @GetMapping("/id/{productId}")
  @Operation(description = "Find product by id")
  public ResponseEntity<Product> findProductById(@PathVariable Long productId)
      throws ProductException {
    Product product = productService.findProductById(productId);
    return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
  }
}
