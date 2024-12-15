package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Category;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.repository.CategoryRepository;
import com.zbodya.ecomerce_back.repository.ProductRepository;
import com.zbodya.ecomerce_back.request.CreateProductRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductServiceImpl(
      ProductRepository productRepository,
      CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public Product createProduct(CreateProductRequest request) {
    Category topLevel = categoryRepository.findByName(request.getTopLevelCategory());
    if (topLevel == null) {
      Category topLevelCategory = new Category();
      topLevelCategory.setName(request.getTopLevelCategory());
      topLevelCategory.setLevel(1);
      topLevel = categoryRepository.save(topLevelCategory);
    }

    Category secondLevel =
        categoryRepository.findByNameAndParentCategory(
            request.getSecondLevelCategory(), topLevel.getName());
    if (secondLevel == null) {
      Category secondLevelCategory = new Category();
      secondLevelCategory.setName(request.getSecondLevelCategory());
      secondLevelCategory.setLevel(2);
      secondLevelCategory.setParentCategory(topLevel);
      secondLevel = categoryRepository.save(secondLevelCategory);
    }

    Category thirdLevel =
        categoryRepository.findByNameAndParentCategory(
            request.getThirdLevelCategory(), secondLevel.getName());
    if (thirdLevel == null) {
      Category thirdLevelCategory = new Category();
      thirdLevelCategory.setName(request.getThirdLevelCategory());
      thirdLevelCategory.setLevel(3);
      thirdLevelCategory.setParentCategory(secondLevel);
      thirdLevel = categoryRepository.save(thirdLevelCategory);
    }

    Product product = new Product();
    product.setBrand(request.getBrand());
    product.setTitle(request.getTitle());
    product.setColor(request.getColor());
    product.setDescription(request.getDescription());
    product.setDiscountedPrice(request.getDiscountedPrice());
    product.setDiscountPresent(request.getDiscountPresent());
    product.setDiscountPercent(request.getDiscountPercent());
    product.setImageUrl(request.getImageUrl());
    product.setPrice(request.getPrice());
    product.setSizes(request.getSizes());
    product.setQuantity(request.getQuantity());
    product.setCategory(thirdLevel);
    product.setCreatedAt(LocalDateTime.now());

    return productRepository.save(product);
  }

  @Override
  public String deleteProduct(Long productId) throws ProductException {
    Product product = findProductById(productId);
    product.getSizes().clear();
    productRepository.delete(product);
    return "Product deleted successfully";
  }

  @Override
  public Product updateProduct(Long productId, CreateProductRequest req) throws ProductException {
    Product product = findProductById(productId);
    product.setId(productId);
    product.setImageUrl(req.getImageUrl());
    product.setColor(req.getColor());
    product.setBrand(req.getBrand());
    product.setTitle(req.getTitle());
    product.setSizes(req.getSizes());
    Category topLevelCategory = product.getCategory();
    topLevelCategory.setName(req.getTopLevelCategory());
    Category secondLevelCategory = topLevelCategory.getParentCategory();
    secondLevelCategory.setName(req.getSecondLevelCategory());
    Category thirdLevelCategory = secondLevelCategory.getParentCategory();
    thirdLevelCategory.setName(req.getThirdLevelCategory());
    secondLevelCategory.setParentCategory(thirdLevelCategory);
    topLevelCategory.setParentCategory(secondLevelCategory);
    product.setCategory(topLevelCategory);
    product.setDescription(req.getDescription());
    product.setPrice(req.getPrice());
    product.setDiscountedPrice(req.getDiscountedPrice());
    product.setDiscountPercent(req.getDiscountPercent());
    return productRepository.save(product);
  }

  @Override
  public Product findProductById(Long productId) throws ProductException {
    Optional<Product> product = productRepository.findById(productId);
    if (product.isPresent()) {
      return product.get();
    }
    throw new ProductException("Product not found with id: " + productId);
  }

  @Override
  public List<Product> findProductByCategory(String category) {

    return List.of();
  }

  @Override
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
      Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Product> products =
        productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
    if (!colors.isEmpty()) {
      products =
          products.stream()
              .filter(
                  product -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(product.getColor())))
              .toList();
    }

    if (stock != null) {
      if (stock.equals("in_stock")) {
        products = products.stream().filter(product -> product.getQuantity() > 0).toList();
      } else if (stock.equals("out_of_stock")) {
        products = products.stream().filter(product -> product.getQuantity() < 1).toList();
      }
    }

    int startIndex = (int) pageable.getOffset();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
    List<Product> pageContent = products.subList(startIndex, endIndex);
    return new PageImpl<>(pageContent, pageable, products.size());
  }

  @Override
  public Page<Product> getAllProductsAdmin(Integer pageNumber, Integer pageSize){
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Product> products = findAllProducts();
    int startIndex = (int) pageable.getOffset();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
    List<Product> pageContent = products.subList(startIndex, endIndex);
    return new PageImpl<>(pageContent, pageable, products.size());
  }
}
