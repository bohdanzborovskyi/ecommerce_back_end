package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.request.CreateProductRequest;
import com.zbodya.ecomerce_back.response.ApiResponse;
import com.zbodya.ecomerce_back.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

  @Autowired private ProductService productService;

  @PostMapping("/")
  public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
    Product product = productService.createProduct(request);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @DeleteMapping("/{productId}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
      throws ProductException {
    productService.deleteProduct(productId);
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Product deleted successfully");
    apiResponse.setStatus(true);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Product>> findAllProducts() {
    List<Product> products = productService.findAllProducts();
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @PutMapping("/{productId}/update")
  public ResponseEntity<Product> updateProduct(
      @RequestBody Product product, @PathVariable Long productId) throws ProductException {
    Product updatedProduct = productService.updateProduct(productId, product);
    return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
  }

  @PostMapping("/createProducts")
  public ResponseEntity<ApiResponse> createMultipleProducts(
      @RequestBody CreateProductRequest[] products) {
    for (CreateProductRequest productRequest : products) {
      productService.createProduct(productRequest);
    }
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Products created successfully");
    apiResponse.setStatus(true);
    return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
  }
}
