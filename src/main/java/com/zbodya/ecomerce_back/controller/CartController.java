package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.CartItemException;
import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Cart;
import com.zbodya.ecomerce_back.model.CartItem;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.request.AddItemRequest;
import com.zbodya.ecomerce_back.request.UpdateItemRequest;
import com.zbodya.ecomerce_back.response.ApiResponse;
import com.zbodya.ecomerce_back.service.CartItemService;
import com.zbodya.ecomerce_back.service.CartService;
import com.zbodya.ecomerce_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart management", description = "Find user carts, add items to cart")
public class CartController {

  @Autowired private CartService cartService;
  @Autowired private CartItemService cartItemService;

  @Autowired private UserService userService;

  @GetMapping("/")
  @Operation(description = "Find cart by user id")
  public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt)
      throws UserException {
    User user = userService.findUserProfileByJwt(jwt);
    Cart cart = cartService.findUserCart(user);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @PutMapping("/add")
  @Operation(description = "Add item to cart")
  public ResponseEntity<ApiResponse> addItemToCart(
      @RequestBody AddItemRequest request, @RequestHeader("Authorization") String jwt)
      throws UserException, ProductException {
    User user = userService.findUserProfileByJwt(jwt);
    cartService.addCartItem(user.getId(), request);

    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Item added to cart");
    apiResponse.setStatus(true);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @DeleteMapping("/remove/{cartItemId}")
  @Operation(description = "Remove cart item from cart")
  public ResponseEntity<Long> removeCartItemFromCart(
      @PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt)
      throws UserException, CartItemException {
    User user = userService.findUserProfileByJwt(jwt);
    cartItemService.removeCartItem(user.getId(), cartItemId);

    return new ResponseEntity<>(cartItemId, HttpStatus.OK);
  }

  @DeleteMapping("/remove")
  @Operation(description = "Remove whole cart")
  public ResponseEntity<String> removeWholeCart(@RequestHeader("Authorization") String jwt)
          throws UserException, CartItemException {
    User user = userService.findUserProfileByJwt(jwt);
    cartService.deleteCart(user);
    return new ResponseEntity<>("Cart was emptied for user with ID " + user.getId() ,HttpStatus.OK);
  }

  @PutMapping("/update/{cartItemId}")
  @Operation(description = "Update cart item from cart")
  public ResponseEntity<CartItem> updateCartItemFromCart(
          @PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt,
          @RequestBody UpdateItemRequest updateItemRequest)
          throws UserException, CartItemException {
    User user = userService.findUserProfileByJwt(jwt);
    Cart cart = cartService.findUserCart(user);
    CartItem updated = new CartItem();
    for(CartItem cartItem : cart.getCartItems()){
      if(cartItem.getId().equals(cartItemId)){
        cartItem.setQuantity(updateItemRequest.getQuantity());
        cartItemService.updateCadtItem(user.getId(), cartItemId, cartItem);
        updated = cartItem;
      }
    }
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }
}
