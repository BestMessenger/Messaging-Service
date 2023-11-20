package com.messenger.message_service.utils.inputMessageFromKafkaTemplate;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class SendMessageFromKafka implements MessageFromKafka {
    private Long group_id;
    private Long id;
    private String send_datetime;
    private Long sender_id;
    private String file_uri;
    private String message;
    private String message_type;

    @Override
    public Long getUser_id() {
        return this.sender_id;
    }

    @Override
    public Long getGroup_id() {
        return this.group_id;
    }
}

