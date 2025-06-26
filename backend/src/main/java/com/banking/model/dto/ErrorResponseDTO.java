package com.banking.model.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO {
    private final String message;
    private String error;
    private String timestamp;

        public ErrorResponseDTO(String message, String error) { 
         this.message = message;
         this.error = error;
         this.timestamp = LocalDateTime.now().toString(); 
        }
    public String getMessage() { return message; }
    public String getError() { return error; }
    public String getTimestamp() { return timestamp; }
}
