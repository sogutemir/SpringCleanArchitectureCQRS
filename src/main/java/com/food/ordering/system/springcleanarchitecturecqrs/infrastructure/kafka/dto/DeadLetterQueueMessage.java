package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeadLetterQueueMessage {
    private String originalTopic;
    private String originalMessage;
    private String errorMessage;
    private LocalDateTime timestamp;

    public DeadLetterQueueMessage(String originalTopic, String originalMessage, String errorMessage) {
        this.originalTopic = originalTopic;
        this.originalMessage = originalMessage;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}