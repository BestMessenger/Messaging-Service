package com.messenger.message_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    private int sentFromId;

    private LocalDateTime timestamp;

    private UUID messageId;

    private String content_type;

    private String media_url;

    private int groupId;
}
