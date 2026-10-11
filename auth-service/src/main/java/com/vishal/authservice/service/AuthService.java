package com.vishal.authservice.service;

import com.vishal.authservice.dto.request.LoginRequest;
import com.vishal.authservice.dto.request.RegisterRequest;
import com.vishal.authservice.dto.response.LoginResponse;
import com.vishal.authservice.entity.User;
import com.vishal.authservice.enums.Role;
import com.vishal.authservice.exception.InvalidCredentialsException;
import com.vishal.authservice.exception.UserAlreadyExistsException;
import com.vishal.authservice.exception.UserNotFoundException;
import com.vishal.authservice.repository.UserRepository;
import com.vishal.authservice.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public void register(RegisterRequest request){
        if (userRepository.findByUsername(request.username()).isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.ROLE_USER);
        user.setActive(true);
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid username or password.");
        }
        String token = jwtService.generateToken(user);
        return new LoginResponse(token, user.getRole());
    }


}
