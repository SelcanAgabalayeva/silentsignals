package com.silentsignals.silentsignals.service;

import com.silentsignals.silentsignals.dto.SosRequestDto;
import org.springframework.security.core.Authentication;

public interface SosService {
    void sendSOS(SosRequestDto request, Authentication auth);
    void resolveSOS(Long userId);
}
