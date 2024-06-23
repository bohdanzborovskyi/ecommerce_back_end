package com.zbodya.ecomerce_back.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

  SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  public String generateToken(Authentication authentication) {
    return Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + JwtConstant.JWT_EXPIRATION_TIME))
        .claim("email", authentication.getName())
        .signWith(key)
        .compact();
  }

  public String getEmailFromToken(String jwt) {
    jwt = jwt.substring(7);
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
    return String.valueOf(claims.get("email"));
  }
}
