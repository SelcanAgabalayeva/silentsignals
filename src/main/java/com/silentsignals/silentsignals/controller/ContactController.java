package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.entity.Contact;
import com.silentsignals.silentsignals.entity.User;
import com.silentsignals.silentsignals.repository.ContactRepository;
import com.silentsignals.silentsignals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    @PostMapping
    public Contact add(@RequestBody Contact c) {
        if (c.getUser() != null && c.getUser().getId() != null) {
            User user = userRepository.findById(c.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User tapılmadı"));
            c.setUser(user);
        }
        return contactRepository.save(c);
    }
    @GetMapping
    public List<Contact> all() {
        return contactRepository.findAll();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }
}