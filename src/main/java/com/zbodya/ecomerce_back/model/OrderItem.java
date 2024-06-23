package com.zbodya.ecomerce_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;

  @JsonIgnore @ManyToOne private Order order;

  @ManyToOne private Product product;

  private String size;

  private int quantity;

  private Integer price;

  private Integer discountedPrice;

  private Long userId;

  private LocalDateTime deliveryDate;

  OrderItem() {}

  public OrderItem(
      Long orderItemId,
      Order order,
      Product product,
      String size,
      int quantity,
      Integer price,
      Integer discountedPrice,
      Long userId,
      LocalDateTime deliveryDate) {
    this.orderItemId = orderItemId;
    this.order = order;
    this.product = product;
    this.size = size;
    this.quantity = quantity;
    this.price = price;
    this.discountedPrice = discountedPrice;
    this.userId = userId;
    this.deliveryDate = deliveryDate;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getDiscountedPrice() {
    return discountedPrice;
  }

  public void setDiscountedPrice(Integer discountedPrice) {
    this.discountedPrice = discountedPrice;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public LocalDateTime getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }
}
