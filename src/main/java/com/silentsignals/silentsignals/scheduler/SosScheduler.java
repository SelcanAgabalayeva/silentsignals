package com.silentsignals.silentsignals.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SosScheduler {

    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedRate = 60000)
    public void checkSOS() {
        Set<String> keys = redisTemplate.keys("sos:*");
        for (String key : keys) {
            System.out.println("Fallback triggered for " + key);

        }
    }
}
