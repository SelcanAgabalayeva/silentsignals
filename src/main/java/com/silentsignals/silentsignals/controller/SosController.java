package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.dto.SosRequestDto;
import com.silentsignals.silentsignals.entity.SosLog;
import com.silentsignals.silentsignals.repository.SosRepository;
import com.silentsignals.silentsignals.service.SosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sos")
@RequiredArgsConstructor
public class SosController {

    private final SosService sosService;
    private final SosRepository sosRepository;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody SosRequestDto request) {
        sosService.sendSOS(request);
        return ResponseEntity.ok("SOS sent");
    }

    @PostMapping("/resolve/{userId}")
    public ResponseEntity<?> resolve(@PathVariable Long userId) {
        sosService.resolveSOS(userId);
        return ResponseEntity.ok("SOS resolved");
    }

    @GetMapping("/history")
    public List<SosLog> history() {
        return sosRepository.findAll();
    }
}
