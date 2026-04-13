package com.silentsignals.silentsignals.repository;

import com.silentsignals.silentsignals.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByUserUsername(String name);
}
