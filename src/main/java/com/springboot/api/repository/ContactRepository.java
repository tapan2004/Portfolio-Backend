package com.springboot.api.repository;

import com.springboot.api.model.ContactMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<ContactMessage, String> {

    // Find all unread messages
    List<ContactMessage> findByReadFalseOrderByCreatedAtDesc();
}