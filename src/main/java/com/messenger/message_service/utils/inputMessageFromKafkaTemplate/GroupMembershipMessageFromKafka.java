package com.messenger.message_service.utils.inputMessageFromKafkaTemplate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupMembershipMessageFromKafka implements MessageFromKafka {
    private String join_date;
    private Long group_id;
    private Long id;
    private Long offset_message_id;
    private Long user_id;
    private String role_in_group;
    private String status_in_group;
}
