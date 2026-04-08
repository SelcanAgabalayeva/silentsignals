package com.silentsignals.silentsignals.repository;

import com.silentsignals.silentsignals.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}
