package com.messenger.message_service.utils.inputMessageFromKafkaTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInviteMessageFromKafka {
    private Long date_responded;
    private Long date_sent;
    private Long group_id;
    private Long id;
    private Long recipient_id;
    private Long sender_id;
    private String invitation_status;
}

//  "payload": {
//          "before": null,
//          "after": {
//          "date_responded": null,
//          "date_sent": 1699964987248715,
//          "group_id": 1,
//          "id": 1,
//          "recipient_id": 1,
//          "sender_id": 1,
//          "invitation_status": "PENDING"
//          },
