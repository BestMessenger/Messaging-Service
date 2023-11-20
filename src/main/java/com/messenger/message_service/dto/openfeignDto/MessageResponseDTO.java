package com.messenger.message_service.dto.openfeignDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
    private int sentToId;
    private int sentFromId;
    private String timestamp;
    private int messageId;
    private String content_type;
    private String media_url;
    private int groupId;
}
