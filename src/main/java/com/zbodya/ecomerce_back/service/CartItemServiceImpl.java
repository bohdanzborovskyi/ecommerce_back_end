package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.CartItemException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Cart;
import com.zbodya.ecomerce_back.model.CartItem;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.CartItemRepository;
import com.zbodya.ecomerce_back.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCadtItem(Long userId, Long id, CartItem cartItem) throws UserException, CartItemException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(userId);

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.isCartItemExist(cart, product, size, userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        User userById = userService.findUserById(userId);
        if(user.getId().equals(userById.getId())){
            cartItemRepository.deleteById(cartItemId);
        }else {
            throw new UserException("You can`t remove another user item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("Cart Item is not exist with id: " + cartItemId));
    }
}
