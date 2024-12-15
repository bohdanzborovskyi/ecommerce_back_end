package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {this.userService = userService;}


    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNumber") int pageNumber,
            @RequestHeader("Authorization") String jwt){
        Page<User> allUsers = userService.getAllUsers(pageSize, pageNumber);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
