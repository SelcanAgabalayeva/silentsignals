package com.silentsignals.silentsignals.service.impls;

import com.silentsignals.silentsignals.dto.SosRequestDto;
import com.silentsignals.silentsignals.entity.SosLog;
import com.silentsignals.silentsignals.entity.User;
import com.silentsignals.silentsignals.repository.SosRepository;
import com.silentsignals.silentsignals.repository.UserRepository;
import com.silentsignals.silentsignals.service.SosService;
import com.silentsignals.silentsignals.websocket.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class SosServiceImpl implements SosService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SosRepository sosRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    @Override
    public void sendSOS(SosRequestDto request, Authentication auth) {

        User user = userRepository.findByUsername(auth.getName());

        // 🚨 RATE LIMIT CHECK
        String rateKey = "rate:sos:" + user.getId();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(rateKey))) {
            throw new RuntimeException("Çox tez SOS göndərirsən! 1 dəqiqə gözlə.");
        }

        redisTemplate.opsForValue().set(
                rateKey,
                "1",
                Duration.ofMinutes(1)
        );

        // 🔥 SOS SAVE
        redisTemplate.opsForValue().set(
                "sos:" + user.getId(),
                request,
                Duration.ofMinutes(3)
        );

        SosLog log = new SosLog();
        log.setUserId(user.getId());
        log.setStatus("SENT");
        log.setCreatedAt(LocalDateTime.now());

        sosRepository.save(log);

        messagingTemplate.convertAndSend(
                "/topic/sos/" + user.getId(),
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
