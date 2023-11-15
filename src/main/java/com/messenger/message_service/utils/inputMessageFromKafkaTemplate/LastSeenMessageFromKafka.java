package com.messenger.message_service.utils.inputMessageFromKafkaTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastSeenMessageFromKafka {
    private Long userId;
    private LocalDate lastSeenDate;
    private String status;
}
