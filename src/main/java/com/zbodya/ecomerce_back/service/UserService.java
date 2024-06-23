package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.User;

public interface UserService {

  public User findUserById(Long userId) throws UserException;

  public User findUserProfileByJwt(String jwt) throws UserException;
}
