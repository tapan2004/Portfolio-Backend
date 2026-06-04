package com.springboot.api.controller;

import com.springboot.api.dto.ContactRequest;
import com.springboot.api.dto.ContactResponse;
import com.springboot.api.model.ContactMessage;
import com.springboot.api.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponse> submitContact(@Valid @RequestBody ContactRequest request) {
        log.info("Contact form submitted by: {}", request.getEmail());
        ContactMessage saved = contactService.saveMessage(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ContactResponse.ok("Message received! I'll get back to you soon.", saved.getId()));
    }

    @GetMapping
    public ResponseEntity<ContactResponse> getAllMessages() {
        List<ContactMessage> messages = contactService.getAllMessages();
        return ResponseEntity.ok(ContactResponse.ok("Messages fetched", messages));
    }

    @GetMapping("/unread")
    public ResponseEntity<ContactResponse> getUnreadMessages() {
        List<ContactMessage> messages = contactService.getUnreadMessages();
        return ResponseEntity.ok(ContactResponse.ok("Unread messages fetched", messages));
    }
    
    @PatchMapping("/{id}/read")
    public ResponseEntity<ContactResponse> markAsRead(@PathVariable String id) {
        ContactMessage updated = contactService.markAsRead(id);
        return ResponseEntity.ok(ContactResponse.ok("Marked as read", updated));
    }
}