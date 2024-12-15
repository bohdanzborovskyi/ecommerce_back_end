package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.model.*;
import com.zbodya.ecomerce_back.repository.AddressRepository;
import com.zbodya.ecomerce_back.repository.OrderItemRepository;
import com.zbodya.ecomerce_back.repository.OrderRepository;
import com.zbodya.ecomerce_back.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;
  private final OrderItemRepository orderItemRepository;

  public OrderServiceImpl(
      OrderRepository orderRepository,
      CartService cartService,
      AddressRepository addressRepository,
      UserRepository userRepository,
      OrderItemRepository orderItemRepository) {
    this.orderRepository = orderRepository;
    this.cartService = cartService;
    this.addressRepository = addressRepository;
    this.userRepository = userRepository;
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public Order createOrder(User user, Address address) {
    address.setUser(user);
    Address userAddress = addressRepository.save(address);
    user.getAddresses().add(userAddress);
    userRepository.save(user);

    Cart userCart = cartService.findUserCart(user);
    List<OrderItem> orderItems = new ArrayList<>();

    for(CartItem cartItem : userCart.getCartItems()){
      OrderItem orderItem = new OrderItem();
      orderItem.setPrice(cartItem.getPrice());
      orderItem.setProduct(cartItem.getProduct());
      orderItem.setQuantity(cartItem.getQuantity());
      orderItem.setUserId(user.getId());
      orderItem.setSize(cartItem.getSize());
      orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

      OrderItem createdOrderItem = orderItemRepository.save(orderItem);
      orderItems.add(createdOrderItem);
    }

    Order order = new Order();
    order.setUser(user);
    order.setOrderItems(orderItems);
    order.setTotalPrice(userCart.getTotalPrice());
    order.setTotalDiscountedPrice(userCart.getTotalDisccountPrice());
    order.setDiscount(userCart.getDiscount());
    order.setTotalItem(userCart.getTotalItem());
    order.setShippingAddress(address);
    order.setOrderDate(LocalDateTime.now());
    order.setOrderStatus(OrderStatus.PENDING);
    order.getPaymentDetails().setPaymentStatus(PaymentStatus.PENDING);
    order.setCreatedAt(LocalDateTime.now());

    Order savedOrder = orderRepository.save(order);

    for(OrderItem item : orderItems){
      item.setOrder(savedOrder);
      orderItemRepository.save(item);
    }

    return savedOrder;
  }

  @Override
  public Order findOrderById(Long orderId) throws OrderException {
    return orderRepository.findById(orderId).orElseThrow(()-> new OrderException("Order not exist with ID:" + orderId));
  }

  @Override
  public List<Order> usersOrderHistory(Long userId) {
    return orderRepository.getUserOrders(userId);
  }

  @Override
  public Order placedOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus(OrderStatus.PLACED);
    order.getPaymentDetails().setPaymentStatus(PaymentStatus.COMPLETED);
    return orderRepository.save(order);
  }

  @Override
  public Order confirmedOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus(OrderStatus.CONFIRMED);
    return orderRepository.save(order);
  }

  @Override
  public Order shippedOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus(OrderStatus.SHIPPED);
    return orderRepository.save(order);
  }

  @Override
  public Order deliveredOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus(OrderStatus.DELIVERED);
    return orderRepository.save(order);
  }

  @Override
  public Order canceledOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    order.setOrderStatus(OrderStatus.CANCELED);
    return orderRepository.save(order);
  }

  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll().stream().sorted(Comparator.comparing(Order::getId)).collect(Collectors.toList());
  }

  @Override
  public Page<Order> getAllOrdersAdmin(Integer pageNumber, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Order> orders = getAllOrders();
    int startIndex = (int) pageable.getOffset();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), orders.size());
    List<Order> pageContent = orders.subList(startIndex, endIndex);
    return new PageImpl<>(pageContent, pageable, orders.size());
  }

  @Override
  public void deleteOrder(Long orderId) throws OrderException {
    Order order = findOrderById(orderId);
    orderRepository.delete(order);
  }
}
