package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.Cart;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.request.AddItemRequest;

public interface CartService {

  public Cart createCart(User user);

  public String addCartItem(Long userId, AddItemRequest request) throws ProductException;

  public Cart findUserCart(User user);
}
