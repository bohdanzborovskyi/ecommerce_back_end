package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User management", description = "")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt)
      throws UserException {
    User user = userService.findUserProfileByJwt(jwt);
    return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
  }
}
