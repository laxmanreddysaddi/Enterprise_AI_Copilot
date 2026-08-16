package com.enterpriseai.backend.service;

import com.enterpriseai.backend.dto.LoginRequest;
import com.enterpriseai.backend.dto.LoginResponse;
import com.enterpriseai.backend.dto.UserRegistrationRequest;
import com.enterpriseai.backend.entity.User;
import com.enterpriseai.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.enterpriseai.backend.dto.LoginRequest;
import com.enterpriseai.backend.dto.LoginResponse;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtService jwtService;

   public UserService(UserRepository userRepository,
                   BCryptPasswordEncoder passwordEncoder,
                   JwtService jwtService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

    public String registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists";
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully";
    }
  public LoginResponse loginUser(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElse(null);

    if (user == null) {
        return new LoginResponse(null, "Invalid email or password");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        return new LoginResponse(null, "Invalid email or password");
    }

    String token = jwtService.generateToken(user.getEmail());

    return new LoginResponse(token, "Login successful");
}
    
}