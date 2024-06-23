package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.model.Address;
import com.zbodya.ecomerce_back.model.Order;
import com.zbodya.ecomerce_back.model.User;
import java.util.List;

public interface OrderService {

  public Order createOrder(User user, Address address);

  public Order findOrderById(Long orderId) throws OrderException;

  public List<Order> usersOrderHistory(Long userId);

  public Order placedOrder(Long orderId) throws OrderException;

  public Order confirmedOrder(Long orderId) throws OrderException;

  public Order shippedOrder(Long orderId) throws OrderException;

  public Order deliveredOrder(Long orderId) throws OrderException;

  public Order canceledOrder(Long orderId) throws OrderException;

  public List<Order> getAllOrders();

  public void deleteOrder(Long orderId) throws OrderException;
}
