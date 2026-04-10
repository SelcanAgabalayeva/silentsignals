package com.silentsignals.silentsignals.repository;

import com.silentsignals.silentsignals.entity.SosLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SosRepository extends JpaRepository<SosLog,Long> {
    SosLog findTopByUserIdOrderByCreatedAtDesc(Long userId);
}
