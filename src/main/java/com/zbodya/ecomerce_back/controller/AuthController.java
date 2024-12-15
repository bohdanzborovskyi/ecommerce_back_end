package com.zbodya.ecomerce_back.controller;

import com.zbodya.ecomerce_back.config.JwtProvider;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Role;
import com.zbodya.ecomerce_back.model.User;
import com.zbodya.ecomerce_back.repository.UserRepository;
import com.zbodya.ecomerce_back.request.LoginRequest;
import com.zbodya.ecomerce_back.response.AuthResponse;
import com.zbodya.ecomerce_back.service.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final CustomUserServiceImpl customUserService;
  private final RestTemplate restTemplate;

  public AuthController(
          UserRepository userRepository,
          JwtProvider jwtProvider,
          PasswordEncoder passwordEncoder,
          CustomUserServiceImpl customUserService,
          RestTemplate restTemplate) {
    this.userRepository = userRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
    this.customUserService = customUserService;
    this.restTemplate = restTemplate;
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)
      throws UserException {

    String email = user.getEmail();
    String password = user.getPassword();
    String firstName = user.getFirstName();
    String lastName = user.getLastName();
    User isEmailExist = userRepository.findByEmail(email);
    AuthResponse authResponse = new AuthResponse();

    if (isEmailExist != null) {
      authResponse.setMessage("Email is already used with another account");
      return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    User createdUser = new User();
    createdUser.setEmail(email);
    createdUser.setPassword(passwordEncoder.encode(password));
    createdUser.setFirstName(firstName);
    createdUser.setLastName(lastName);
    createdUser.addRole(Role.USER);

    User savedUser = userRepository.save(createdUser);
    List<GrantedAuthority> authorities = new ArrayList<>();
    for(Role role : user.getRoles()){
      authorities.add(new SimpleGrantedAuthority(role.name()));
    }
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword(), authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.generateToken(authentication);
    authResponse.setMessage("Signup Success");
    authResponse.setJwt(token);
    return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> loinUserHandler(@RequestBody LoginRequest loginRequest)
      throws UserException {
    String userName = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    Authentication authentication = authenticate(userName, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.generateToken(authentication);
    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt(token);
    authResponse.setMessage("Signing Successfully");
    return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
  }

  private Authentication authenticate(String userName, String password) {
    UserDetails userDetails = customUserService.loadUserByUsername(userName);
    if (userDetails == null) {
      throw new BadCredentialsException("Invalid username");
    }
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
