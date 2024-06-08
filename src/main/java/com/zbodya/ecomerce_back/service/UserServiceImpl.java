package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.config.JwtProvider;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User was not found for id: " + userId));
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String emailFromToken = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(emailFromToken);
        if(user != null){
            return user;
        }
        throw new UserException("User was not found for id: ");
    }
}
