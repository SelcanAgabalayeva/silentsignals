package com.silentsignals.silentsignals.repository;

import com.silentsignals.silentsignals.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
