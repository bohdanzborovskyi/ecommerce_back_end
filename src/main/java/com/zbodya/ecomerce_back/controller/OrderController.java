package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Address;
import com.zbodya.ecomerce_back.model.Order;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.service.OrderService;
import com.zbodya.ecomerce_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order management", description = "Manage user orders")
public class OrderController {

  @Autowired private OrderService orderService;

  @Autowired private UserService userService;

  @PostMapping("/")
  @Operation(description = "Create user order")
  public ResponseEntity<Order> createOrder(
      @RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt)
      throws UserException {
    User user = userService.findUserProfileByJwt(jwt);
    Order order = orderService.createOrder(user, shippingAddress);
    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

  @GetMapping("/user")
  public ResponseEntity<List<Order>> userOrdersHistory(@RequestHeader("Authorization") String jwt)
      throws UserException {
    User user = userService.findUserProfileByJwt(jwt);
    List<Order> orders = orderService.usersOrderHistory(user.getId());
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> findOrderById(
      @RequestHeader("Authorization") String jwt, @PathVariable Long orderId)
      throws UserException, OrderException {
    User user = userService.findUserProfileByJwt(jwt);
    Order order = orderService.findOrderById(orderId);
    return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
  }
}
