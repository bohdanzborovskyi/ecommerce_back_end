package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.request.CreateProductRequest;
import com.zbodya.ecomerce_back.response.ApiResponse;
import com.zbodya.ecomerce_back.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

  @Autowired private ProductService productService;

  @PostMapping("/create")
  public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
    Product product = productService.createProduct(request);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @DeleteMapping("/{productId}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
    ApiResponse apiResponse = new ApiResponse();
    try{
    productService.deleteProduct(productId);
    }catch (ProductException exception){
      apiResponse.setMessage("There is no product with ID:" + productId);
      apiResponse.setStatus(false);
      return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }catch (Exception exception){
      apiResponse.setMessage("You can`t delete product with ID:" + productId);
      apiResponse.setStatus(false);
      return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    apiResponse.setMessage("Product deleted successfully");
    apiResponse.setStatus(true);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<Page<Product>> findAllProducts(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
    Page<Product> allProductsAdmin = productService.getAllProductsAdmin(pageNumber, pageSize);
    return new ResponseEntity<>(allProductsAdmin, HttpStatus.OK);
  }

  @PutMapping("/{productId}/edit")
  public ResponseEntity<Product> updateProduct(
      @RequestBody CreateProductRequest product, @PathVariable Long productId) throws ProductException {
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
