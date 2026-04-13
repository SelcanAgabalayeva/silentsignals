package com.silentsignals.silentsignals.controller;

import com.silentsignals.silentsignals.dto.ContactRequestDto;
import com.silentsignals.silentsignals.entity.Contact;
import com.silentsignals.silentsignals.entity.User;
import com.silentsignals.silentsignals.repository.ContactRepository;
import com.silentsignals.silentsignals.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping
    public Contact add(@Valid @RequestBody ContactRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User tapılmadı"));

        Contact c = new Contact();
        c.setUser(user);
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());

        return contactRepository.save(c);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping
    public List<Contact> all(Authentication auth) {
        return contactRepository.findByUserUsername(auth.getName());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact tapılmadı"));

        if (!contact.getUser().getUsername().equals(auth.getName())) {
            throw new RuntimeException("Access denied");
        }

        contactRepository.deleteById(id);
    }
}