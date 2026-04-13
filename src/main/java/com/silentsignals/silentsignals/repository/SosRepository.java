package com.silentsignals.silentsignals.repository;

import com.silentsignals.silentsignals.entity.SosLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SosRepository extends JpaRepository<SosLog,Long> {
    SosLog findTopByUserIdOrderByCreatedAtDesc(Long userId);

    List<SosLog> findByUserId(Long id);
}
