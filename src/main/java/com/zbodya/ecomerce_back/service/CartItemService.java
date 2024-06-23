package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.CartItemException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Cart;
import com.zbodya.ecomerce_back.model.CartItem;
import com.zbodya.ecomerce_back.model.Product;

public interface CartItemService {

  public CartItem createCartItem(CartItem cartItem);

  public CartItem updateCadtItem(Long userId, Long id, CartItem cartItem)
      throws UserException, CartItemException;

  public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

  public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

  public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
