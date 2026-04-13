package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.dto.AuthResponse;
import com.silentsignals.silentsignals.dto.RegisterRequestDto;
import com.silentsignals.silentsignals.entity.User;
import com.silentsignals.silentsignals.enums.Role;
import com.silentsignals.silentsignals.repository.UserRepository;
import com.silentsignals.silentsignals.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequestDto request) {

        if (userRepository.findByUsername(request.getUsername()) != null) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return "User registered successfully";
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody RegisterRequestDto request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            String token = jwtUtil.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );

            return new AuthResponse(token, user.getRole().name());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
