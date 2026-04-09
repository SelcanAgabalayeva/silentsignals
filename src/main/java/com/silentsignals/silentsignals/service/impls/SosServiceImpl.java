package com.silentsignals.silentsignals.service.impls;

import com.silentsignals.silentsignals.dto.SosRequest;
import com.silentsignals.silentsignals.entity.SosLog;
import com.silentsignals.silentsignals.repository.SosRepository;
import com.silentsignals.silentsignals.service.SosService;
import com.silentsignals.silentsignals.websocket.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SosServiceImpl implements SosService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final SosRepository sosRepository;
    private final EmailService emailService;

    @Override
    public void sendSOS(SosRequest request) {
        redisTemplate.opsForValue().set(
                "sos:" + request.getUserId(),
                request,
                Duration.ofMinutes(3)
        );

        SosLog log = new SosLog();
        log.setUserId(request.getUserId());
        log.setStatus("SENT");
        log.setCreatedAt(LocalDateTime.now());
        sosRepository.save(log);


        if (request.getEmail() != null) {
            emailService.sendEmail(request.getEmail());
        }
    }
}
