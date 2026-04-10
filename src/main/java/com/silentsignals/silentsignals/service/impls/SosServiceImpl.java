package com.silentsignals.silentsignals.service.impls;

import com.silentsignals.silentsignals.dto.SosRequestDto;
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
    private final SosRepository sosRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendSOS(SosRequestDto request) {

        // 1. Redis TTL (3 dəq)
        redisTemplate.opsForValue().set(
                "sos:" + request.getUserId(),
                request,
                Duration.ofMinutes(3)
        );

        // 2. DB log
        SosLog log = new SosLog();
        log.setUserId(request.getUserId());
        log.setStatus("SENT");
        log.setCreatedAt(LocalDateTime.now());
        sosRepository.save(log);

        // 3. WebSocket push
        messagingTemplate.convertAndSend(
                "/topic/sos/" + request.getUserId(),
                request
        );
    }

    @Override
    public void resolveSOS(Long userId) {

        // Redis sil
        redisTemplate.delete("sos:" + userId);

        // DB update
        SosLog log = sosRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
        if (log != null) {
            log.setStatus("RESOLVED");
            sosRepository.save(log);
        }
    }
}
