package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.entity.User;
import com.silentsignals.silentsignals.repository.UserRepository;
import com.silentsignals.silentsignals.security.JwtUtil;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
