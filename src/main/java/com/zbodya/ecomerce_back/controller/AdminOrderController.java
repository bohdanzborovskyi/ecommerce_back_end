package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.model.Order;
import com.zbodya.ecomerce_back.response.ApiResponse;
import com.zbodya.ecomerce_back.service.OrderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

  private final OrderService orderService;

  public AdminOrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/")
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
  }

  @PutMapping("/{orderId}/confirmed")
  public ResponseEntity<Order> confirmedOrder(
      @PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
      throws OrderException {
    Order order = orderService.confirmedOrder(orderId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PutMapping("/{orderId}/ship")
  public ResponseEntity<Order> shipOrder(
      @PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
      throws OrderException {
    Order order = orderService.shippedOrder(orderId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PutMapping("/{orderId}/deliver")
  public ResponseEntity<Order> deliverOrder(
      @PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
      throws OrderException {
    Order order = orderService.deliveredOrder(orderId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PutMapping("/{orderId}/cancel")
  public ResponseEntity<Order> cancelOrder(
      @PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
      throws OrderException {
    Order order = orderService.canceledOrder(orderId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PutMapping("/{orderId}/delete")
  public ResponseEntity<ApiResponse> deleteOrder(
      @PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
      throws OrderException {
    orderService.deleteOrder(orderId);
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Order deleted successfully");
    apiResponse.setStatus(true);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}
