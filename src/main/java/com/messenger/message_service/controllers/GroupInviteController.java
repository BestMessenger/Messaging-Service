package com.messenger.message_service.controllers;

import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.GroupInviteMessageFromKafka;
import com.messenger.message_service.utils.jsonDeserializer.JsonDeserializer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class GroupInviteController {
    private final JsonDeserializer<GroupInviteMessageFromKafka> deserializer;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final static String GROUP_INVITE_TOPIC_NAME = "/inviteToGroup";

    @KafkaListener(topics = "postgres.public.invitations_table", groupId = "group_invite_group")
    public void getNewInvite(String inputMessage) {
        GroupInviteMessageFromKafka messageFromKafka = deserializer.deserialize(inputMessage);
        String destination = GROUP_INVITE_TOPIC_NAME + "/" + messageFromKafka.getRecipient_id();
        simpMessagingTemplate.convertAndSend(destination, messageFromKafka);
        //todo перепроверить
    }
}
