package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.model.Address;
import com.zbodya.ecomerce_back.model.Order;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.CartRepository;
import com.zbodya.ecomerce_back.repository.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;
  private final CartService cartService;
  private final ProductService productService;

  public OrderServiceImpl(
      OrderRepository orderRepository,
      CartRepository cartRepository,
      CartService cartService,
      ProductService productService) {
    this.orderRepository = orderRepository;
    this.cartRepository = cartRepository;
    this.cartService = cartService;
    this.productService = productService;
  }

  @Override
  public Order createOrder(User user, Address address) {
    return null;
  }

  @Override
  public Order findOrderById(Long orderId) throws OrderException {
    return null;
  }

  @Override
  public List<Order> usersOrderHistory(Long userId) {
    return List.of();
  }

  @Override
  public Order placedOrder(Long orderId) throws OrderException {
    return null;
  }

  @Override
  public Order confirmedOrder(Long orderId) throws OrderException {
    return null;
  }

  @Override
  public Order shippedOrder(Long orderId) throws OrderException {
    return null;
  }

  @Override
  public Order deliveredOrder(Long orderId) throws OrderException {
    return null;
  }

  @Override
  public Order canceledOrder(Long orderId) throws OrderException {
    return null;
  }

  @Override
  public List<Order> getAllOrders() {
    return List.of();
  }

  @Override
  public void deleteOrder(Long orderId) throws OrderException {}
}
