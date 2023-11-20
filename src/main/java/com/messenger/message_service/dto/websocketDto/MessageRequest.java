package com.messenger.message_service.dto.websocketDto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class MessageRequest {
    private String message;
    private Long groupId;
    private Long senderId;
}
