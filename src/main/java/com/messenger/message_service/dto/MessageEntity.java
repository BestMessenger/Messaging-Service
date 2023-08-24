package com.messenger.message_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {
    private int sentToId;

    private int sentFromId;

    private LocalDateTime timastamp;

    private int messageId;

    private String content_type;

    private String media_url;

    private int groupId;
}
