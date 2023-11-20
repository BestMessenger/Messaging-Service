package com.messenger.message_service.utils.inputMessageFromKafkaTemplate;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class InviteMessageFromKafka implements MessageFromKafka {
    private Timestamp date_responded;
    private Timestamp date_sent;
    private Long group_id;
    private Long id;
    private Long recipient_id;
    private Long sender_id;
    private String invitation_status;
    private String offset_messages;

    @Override
    public Long getUser_id() {
        return this.recipient_id;
    }

    @Override
    public Long getGroup_id() {
        return this.group_id;
    }
}