package com.messenger.message_service.dto.websocketDto;

import com.messenger.message_service.utils.enums.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {
    private Long id;
    private MessageTypeEnum messageType;
    private String message;
    private Long groupId;
    private Long senderId;
    private LocalDateTime sendDatetime;
    private String fileUrl;
}
