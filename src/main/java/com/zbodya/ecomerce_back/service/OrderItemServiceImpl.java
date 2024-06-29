package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.model.OrderItem;
import com.zbodya.ecomerce_back.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

  private final OrderItemRepository orderItemRepository;

  public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public OrderItem createOrderItem(OrderItem orderItem) {
    return orderItemRepository.save(orderItem);
  }
}
