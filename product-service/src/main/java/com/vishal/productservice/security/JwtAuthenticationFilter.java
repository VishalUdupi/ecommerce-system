package com.vishal.productservice.security;

import com.vishal.productservice.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService service;
    public JwtAuthenticationFilter(JwtService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthenticationServiceException("Authorization header missing");
        }
        String token = header.replace("Bearer ", "");
        if (service.isTokenExpired(token)) {
            throw new AuthenticationServiceException("Token expired");
        }
        String username;
        String role;
        try {
            username = service.extractUsername(token);
            role = service.extractRole(token);
        } catch (JwtException e) {
            throw new AuthenticationServiceException("Invalid token");
        }
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                username,
                null,
                authorities
        );
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);
        }
}
