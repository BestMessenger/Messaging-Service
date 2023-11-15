package com.messenger.message_service.controllers;

import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.LastSeenMessageFromKafka;
import com.messenger.message_service.utils.jsonDeserializer.JsonDeserializer;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@PropertySource("classpath:application.yaml")
public class LastSeenController {

    private final JsonDeserializer<LastSeenMessageFromKafka> deserializer;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final static String LAST_SEEN_TOPIC_NAME = "/lastSeenUser";

    @KafkaListener(topics = "postgres.public.invitations_table", groupId = "group_invite_group")
    public void getNewInvite(String inputMessage) {
        LastSeenMessageFromKafka messageFromKafka = deserializer.deserialize(inputMessage);
        String destination = "/lastSeen/" + messageFromKafka.getUserId();
        simpMessagingTemplate.convertAndSend(destination, messageFromKafka);
        //todo перепроверить и исправить
    }
}
