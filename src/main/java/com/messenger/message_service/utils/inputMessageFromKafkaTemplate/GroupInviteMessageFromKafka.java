package com.messenger.message_service.utils.inputMessageFromKafkaTemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInviteMessageFromKafka implements MessageFromKafka {
    private Long date_responded;
    private Long date_sent;
    private Long group_id;
    private Long id;
    private Long recipient_id;
    private Long sender_id;
    private String invitation_status;

    @Override
    public Long getUser_id() {
        return this.recipient_id;
    }

    @Override
    public Long getGroup_id() {
        return this.group_id;
    }
}
