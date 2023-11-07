package com.messenger.message_service.controllers.strategy;

import com.messenger.message_service.dto.InputTransportDTO;
import com.messenger.message_service.dto.MessageEntity;
import com.messenger.message_service.dto.OutputTransportDto;
import com.messenger.message_service.openfeign_client.GroupServiceClient;
import com.messenger.message_service.openfeign_client.RelayServiceClient;
import com.messenger.message_service.utils.TransportActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchGroupMessagesStrategy implements MessageActionStrategy {

    public static int messageId; // todo исправить
    private final GroupServiceClient groupServiceClient;
    private final RelayServiceClient relayServiceClient;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public FetchGroupMessagesStrategy(GroupServiceClient groupServiceClient, RelayServiceClient relayServiceClient, SimpMessagingTemplate messagingTemplate) {
        this.groupServiceClient = groupServiceClient;
        this.relayServiceClient = relayServiceClient;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void execute(InputTransportDTO input) {
        if (groupServiceClient.getGroupByGroupId(input.getGroupId()).getBody() != null) {
            List<MessageEntity> messages = relayServiceClient.getMessagesByGroupId(input.getGroupId()).getBody();
            OutputTransportDto output = new OutputTransportDto(TransportActionEnum.FETCH_GROUP_MESSAGES, messages);
            messagingTemplate.convertAndSend("/topic/user/" + input.getUserId(), output);
        }
    }
}
