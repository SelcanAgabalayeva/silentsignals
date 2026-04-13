package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.dto.SosRequestDto;
import com.silentsignals.silentsignals.entity.SosLog;
import com.silentsignals.silentsignals.entity.User;
import com.silentsignals.silentsignals.repository.SosRepository;
import com.silentsignals.silentsignals.repository.UserRepository;
import com.silentsignals.silentsignals.service.SosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
@RequiredArgsConstructor
public class SosController {

    private final SosService sosService;
    private final SosRepository sosRepository;
    private final UserRepository userRepository;
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/send")
    public ResponseEntity<?> send(@Valid @RequestBody SosRequestDto request,Authentication auth) {
        sosService.sendSOS(request,auth);
        return ResponseEntity.ok("SOS sent");
    }
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/resolve/{userId}")
    public ResponseEntity<?> resolve(@PathVariable Long userId) {
        sosService.resolveSOS(userId);
        return ResponseEntity.ok("SOS resolved");
    }
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/history")
    public List<SosLog> history(Authentication auth) {

        User user = userRepository.findByUsername(auth.getName());

        return sosRepository.findByUserId(user.getId());
    }
}
