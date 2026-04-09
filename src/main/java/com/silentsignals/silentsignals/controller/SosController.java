package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.dto.SosRequest;
import com.silentsignals.silentsignals.entity.SosLog;
import com.silentsignals.silentsignals.repository.SosRepository;
import com.silentsignals.silentsignals.service.SosService;
import com.silentsignals.silentsignals.service.impls.SosServiceImpl;
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
    public ResponseEntity<?> send(@RequestBody SosRequest request) {
        sosService.sendSOS(request);
        return ResponseEntity.ok("SOS sent");
    }

    @GetMapping("/history")
    public List<SosLog> history() {
        return sosRepository.findAll();
    }
}
