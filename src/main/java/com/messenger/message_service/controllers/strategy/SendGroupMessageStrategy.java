package com.messenger.message_service.controllers.strategy;

import com.messenger.message_service.controllers.WsController;
import com.messenger.message_service.dto.InputTransportDTO;
import com.messenger.message_service.dto.MessageEntity;
import com.messenger.message_service.dto.openfeignDto.GroupResponse;
import com.messenger.message_service.openfeign_client.GroupServiceClient;
import com.messenger.message_service.openfeign_client.RelayServiceClient;
import com.messenger.message_service.utils.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SendGroupMessageStrategy implements MessageActionStrategy {

    private final GroupServiceClient groupServiceClient;
    private final RelayServiceClient relayServiceClient;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SendGroupMessageStrategy(GroupServiceClient groupServiceClient, RelayServiceClient relayServiceClient, SimpMessagingTemplate messagingTemplate) {
        this.groupServiceClient = groupServiceClient;
        this.relayServiceClient = relayServiceClient;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void execute(InputTransportDTO input) {
        if (groupServiceClient.getGroupByGroupId(input.getGroupId()).getBody() != null) {
            MessageEntity message = new MessageEntity();
            message.setMessageId(WsController.messageId++); // todo поменять занчения в базе данных
            message.setGroupId(input.getGroupId());
            message.setContent_type(MessageTypeEnum.TEXT.toString());
            message.setSentFromId(input.getUserId());
            message.setTimastamp(LocalDateTime.now());
            message.setMedia_url(input.getMessage());
            message.setSentToId(input.getUserId());
            relayServiceClient.saveMessage(message);

            List<GroupResponse> groupResponses = groupServiceClient.getAllUsersByGroupId(input.getGroupId()).getBody();

            for (GroupResponse groupResponse : groupResponses) {
                messagingTemplate.convertAndSend("/group/" + groupResponse.getGroup_id(), message);
            }
        }


    }
}
