package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.config.JwtProvider;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Product;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private JwtProvider jwtProvider;

  public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
    this.userRepository = userRepository;
    this.jwtProvider = jwtProvider;
  }

  @Override
  public User findUserById(Long userId) throws UserException {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new UserException("User was not found for id: " + userId));
  }

  @Override
  public User findUserProfileByJwt(String jwt) throws UserException {
    String emailFromToken = jwtProvider.getEmailFromToken(jwt);
    User user = userRepository.findByEmail(emailFromToken);
    if (user != null) {
      return user;
    }
    throw new UserException("User was not found for id: ");
  }

  @Override
  public Page<User> getAllUsers(Integer pageSize, Integer pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<User> users = userRepository.findAll();
    int startIndex = (int) pageable.getOffset();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), users.size());
    List<User> pageContent = users.subList(startIndex, endIndex);
    return new PageImpl<>(pageContent, pageable, users.size());
  }

}
