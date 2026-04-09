package com.silentsignals.silentsignals.service;

import com.silentsignals.silentsignals.dto.SosRequest;

public interface SosService {
    void sendSOS(SosRequest request);
}
