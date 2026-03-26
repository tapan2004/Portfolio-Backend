package com.springboot.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ContactResponse ok(String message, Object data) {
        return new ContactResponse(true, message, data);
    }

    public static ContactResponse fail(String message) {
        return new ContactResponse(false, message, null);
    }
}