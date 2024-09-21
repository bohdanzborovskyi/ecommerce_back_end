package com.zbodya.ecomerce_back.service;

import com.zbodya.ecomerce_back.model.MyUserDetails;
import com.zbodya.ecomerce_back.model.Role;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
public class CustomUserServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  public CustomUserServiceImpl(){}

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found by e-mail " + username);
    }
    return new MyUserDetails(user);
  }
}
