package com.vishal.authservice.security;

import com.vishal.authservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;


    private SecretKey getSigningKey(){
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(User user){
        Instant instant = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role",user.getRole())
                .issuedAt(Date.from(instant))
                .expiration(Date.from(instant.plusMillis(expiration)))
                .signWith(getSigningKey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        Instant now = Instant.now();
        Date currentDate = Date.from(now);
        return currentDate.after(extractExpiration(token));
    }

    public boolean isTokenValid(String token, User user){
        return !(isTokenExpired(token)) && extractUsername(token).equals(user.getUsername());
    }
}
