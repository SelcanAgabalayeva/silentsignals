package com.silentsignals.silentsignals.service;

import com.silentsignals.silentsignals.dto.SosRequestDto;

public interface SosService {
    void sendSOS(SosRequestDto request);
    void resolveSOS(Long userId);
}
