package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.ProductException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Cart;
import com.zbodya.ecomerce_back.model.CartItem;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.CartRepository;
import com.zbodya.ecomerce_back.request.AddItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final CartItemService cartItemService;
  private final ProductService productService;
  private final UserService userService;

  public CartServiceImpl(
      CartRepository cartRepository,
      CartItemService cartItemService,
      ProductService productService,
      UserService userService) {
    this.cartRepository = cartRepository;
    this.cartItemService = cartItemService;
    this.productService = productService;
    this.userService = userService;
  }

  @Override
  public Cart createCart(User user) {
    Cart cart = new Cart();
    cart.setUser(user);
    return cartRepository.save(cart);
  }

  @Override
  public String addCartItem(Long userId, AddItemRequest request) throws ProductException, UserException {
    User user = userService.findUserById(userId);
    Cart cart =
            cartRepository.findByUserId(user.getId()) == null
                    ? createCart(user)
                    : cartRepository.findByUserId(user.getId());
    Product product = productService.findProductById(request.getProductId());
    CartItem cartItem = cartItemService.isCartItemExist(cart, product, request.getSize(), userId);
    if (cartItem == null) {
      CartItem item = new CartItem();
      item.setProduct(product);
      item.setCart(cart);
      item.setQuantity(request.getQuantity());
      item.setUserId(userId);
      int price = request.getQuantity() * product.getDiscountedPrice();
      item.setPrice(price);
      item.setSize(request.getSize());
      CartItem createdCartItem = cartItemService.createCartItem(item);
      cart.getCartItems().add(createdCartItem);
    }
    return "Item added to the cart successfully";
  }

  @Override
  public Cart findUserCart(User user) {
    Cart cart =
        cartRepository.findByUserId(user.getId()) == null
            ? createCart(user)
            : cartRepository.findByUserId(user.getId());
    int totalPrice = 0;
    int totalDiscountedPrice = 0;
    int totalItem = 0;
    for (CartItem cartItem : cart.getCartItems()) {
      totalPrice = totalPrice + cartItem.getPrice();
      totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
      totalItem = totalItem + cartItem.getQuantity();
    }
    cart.setUser(user);
    cart.setTotalPrice(totalPrice);
    cart.setTotalItem(totalItem);
    cart.setTotalDisccountPrice(totalDiscountedPrice);
    cart.setDiscount(totalPrice - totalDiscountedPrice);

    return cartRepository.save(cart);
  }
}
