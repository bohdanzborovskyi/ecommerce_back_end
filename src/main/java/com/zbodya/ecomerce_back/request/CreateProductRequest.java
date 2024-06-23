package com.zbodya.ecomerce_back.request;

import com.zbodya.ecomerce_back.model.Size;
import java.util.HashSet;
import java.util.Set;

public class CreateProductRequest {

  private String title;
  private String description;
  private int price;
  private int discountedPrice;
  private int discountPresent;
  private int discountPercent;
  private int quantity;
  private String color;
  private String brand;
  private Set<Size> sizes = new HashSet<>();
  private String imageUrl;
  private String topLevelCategory;
  private String secondLevelCategory;
  private String thirdLevelCategory;

  public int getDiscountPercent() {
    return discountPercent;
  }

  public void setDiscountPercent(int discountPercent) {
    this.discountPercent = discountPercent;
  }

  public String getThirdLevelCategory() {
    return thirdLevelCategory;
  }

  public void setThirdLevelCategory(String thirdLevelCategory) {
    this.thirdLevelCategory = thirdLevelCategory;
  }

  public String getSecondLevelCategory() {
    return secondLevelCategory;
  }

  public void setSecondLevelCategory(String secondLevelCategory) {
    this.secondLevelCategory = secondLevelCategory;
  }

  public String getTopLevelCategory() {
    return topLevelCategory;
  }

  public void setTopLevelCategory(String topLevelCategory) {
    this.topLevelCategory = topLevelCategory;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Set<Size> getSizes() {
    return sizes;
  }

  public void setSizes(Set<Size> sizes) {
    this.sizes = sizes;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getDiscountPresent() {
    return discountPresent;
  }

  public void setDiscountPresent(int discountPresent) {
    this.discountPresent = discountPresent;
  }

  public int getDiscountedPrice() {
    return discountedPrice;
  }

  public void setDiscountedPrice(int discountedPrice) {
    this.discountedPrice = discountedPrice;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
