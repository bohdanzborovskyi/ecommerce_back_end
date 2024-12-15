package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.request.CreateProductRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

  public List<Product> findAllProducts();

  public Product createProduct(CreateProductRequest request);

  public String deleteProduct(Long productId) throws ProductException;

  public Product updateProduct(Long productId, CreateProductRequest req) throws ProductException;

  public Product findProductById(Long productId) throws ProductException;

  public List<Product> findProductByCategory(String category);

  public Page<Product> getAllProduct(
      String category,
      List<String> colors,
      List<String> sizes,
      Integer minPrice,
      Integer maxPrice,
      Integer minDiscount,
      String sort,
      String stock,
      Integer pageNumber,
      Integer pageSize);

  public Page<Product> getAllProductsAdmin(Integer pageNumber, Integer pageSize);
}
