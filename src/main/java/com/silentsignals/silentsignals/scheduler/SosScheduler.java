package com.silentsignals.silentsignals.scheduler;

import com.silentsignals.silentsignals.dto.SosRequestDto;
import com.silentsignals.silentsignals.entity.SosLog;
import com.silentsignals.silentsignals.repository.SosRepository;
import com.silentsignals.silentsignals.websocket.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SosScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SosRepository sosRepository;
    private final EmailService emailService;


    @Scheduled(fixedRate = 60000)
    public void checkSOS() {

        System.out.println("🔍 CHECK SOS RUNNING");

        Set<String> keys = redisTemplate.keys("sos:*");

        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {

            System.out.println("CHECKING KEY: " + key);

            Object obj = redisTemplate.opsForValue().get(key);

            if (obj instanceof SosRequestDto request) {

                System.out.println("🚨 FALLBACK TRIGGERED: " + key);

                // EMAIL fallback
                if (request.getEmail() != null) {
                    emailService.sendEmail(request.getEmail());
                    System.out.println("📧 EMAIL SENT: " + request.getEmail());
                }

                // 🔥 FIX: userId Redis key-dən çıxarılır
                String userIdStr = key.replace("sos:", "");
                Long userId = Long.valueOf(userIdStr);

                SosLog log = sosRepository
                        .findTopByUserIdOrderByCreatedAtDesc(userId);

                if (log != null) {
                    log.setStatus("ESCALATED");
                    sosRepository.save(log);
                }

                redisTemplate.delete(key);
            }
        }
    }
}
