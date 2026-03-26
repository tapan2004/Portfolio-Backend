package com.springboot.api.service;

import com.springboot.api.dto.ContactRequest;
import com.springboot.api.model.ContactMessage;
import com.springboot.api.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    //Save a new contact message to MongoDB
    public ContactMessage saveMessage(ContactRequest request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .subject(request.getSubject())
                .message(request.getMessage())
                .build();

        ContactMessage saved = contactRepository.save(message);
        log.info("New contact message saved — id: {}, from: {}", saved.getId(), saved.getEmail());
        emailService.sendOwnerNotification(saved);
        emailService.sendAutoReply(saved);
        return saved;
    }

    //Get all contact messages (admin use)
    public List<ContactMessage> getAllMessages() {
        return contactRepository.findAll();
    }

    // Get all unread messages
    public List<ContactMessage> getUnreadMessages() {
        return contactRepository.findByReadFalseOrderByCreatedAtDesc();
    }

    // Mark a message as read
    public ContactMessage markAsRead(String id) {
        ContactMessage msg = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        msg.setRead(true);
        return contactRepository.save(msg);
    }
}