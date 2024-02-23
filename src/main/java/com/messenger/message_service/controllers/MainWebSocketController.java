package com.messenger.message_service.controllers;

import com.messenger.message_service.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MainWebSocketController {
    private final WebSocketService webSocketService;

    @KafkaListener(topics = "messenger.public.message_table", groupId = "messenger")
    public void sendMessageToTopic(String inputMessage) {
        webSocketService.processSendMessageFromKafka(inputMessage);
    }

    @KafkaListener(topics = "messenger.public.group_memberships_table", groupId = "messenger")
    public void changeOrLeaveOperation(String inputMessage) {
        webSocketService.processChangeOrLeaveOperation(inputMessage);
    }

    @KafkaListener(topics = "messenger.public.invitations_table", groupId = "messenger")
    public void inviteUserToGroup(@Payload String inputMessage) {
        webSocketService.processInviteUserToGroup(inputMessage);
    }

}
