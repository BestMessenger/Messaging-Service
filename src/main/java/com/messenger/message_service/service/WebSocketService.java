package com.messenger.message_service.service;

import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.repository.MessageRepository;
import com.messenger.message_service.utils.MessageFactory.*;
import com.messenger.message_service.utils.checkOperation.CheckOperation;
import com.messenger.message_service.utils.enums.DebeziumOperation;
import com.messenger.message_service.utils.enums.MessageTypeEnum;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.GroupMembershipMessageFromKafka;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.InviteMessageFromKafka;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.MessageFromKafka;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.SendMessageFromKafka;
import com.messenger.message_service.utils.jsonDeserializer.GroupMembershipDeserializer;
import com.messenger.message_service.utils.jsonDeserializer.GroupMembershipLeaveDeserializer;
import com.messenger.message_service.utils.jsonDeserializer.JsonDeserializer;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final CheckOperation checkOperation;
    private final MessageRepository messageRepository;

    private final JsonDeserializer<SendMessageFromKafka> deserializerSendMessages;
    private final JsonDeserializer<InviteMessageFromKafka> deserializerInviteMessages;
    private final GroupMembershipDeserializer deserializerGroupMembershipAccepted;
    private final GroupMembershipLeaveDeserializer deserializerLeaveGroup;

    public void processSendMessageFromKafka(String inputMessage) {
        SendMessageFromKafka messageFromKafka = deserializerSendMessages.deserialize(inputMessage);
        simpMessagingTemplate.convertAndSend(
                "/group/" + messageFromKafka.getGroup_id(),
                messageRepository.findById(messageFromKafka.getId())
        );
    }

    public void processChangeOrLeaveOperation(String inputMessage) {
        DebeziumOperation operation = checkOperation.deserialize(inputMessage);
        GroupMembershipMessageFromKafka messageFromKafka = getMembershipMessage(operation, inputMessage);
        Message message = getMessageByOperation(operation);
        processMessage(messageFromKafka, message);
    }

    public void processInviteUserToGroup(String inputMessage) {
        if (StringUtils.isEmpty(inputMessage)) {
            return;
        }
        Message message = new InviteToGroupMessage();
        processMessage(deserializerInviteMessages.deserialize(inputMessage), message);
    }

    private void processMessage(MessageFromKafka messageFromKafka, Message message) {
        if (messageFromKafka == null) {
            return;
        }
        MessageModel model = message.createMessage(
                "User " + messageFromKafka.getUser_id() + " did " + message.getType(),
                messageFromKafka.getUser_id(),
                messageFromKafka.getGroup_id(),
                null
        );
        messageRepository.save(model);
        String destination = message.getType().equals(MessageTypeEnum.INVITE_TO_GROUP_MESSAGE) ?
                "/invite_to_group/" + messageFromKafka.getUser_id()
                :
                "/group/" + messageFromKafka.getGroup_id();
        simpMessagingTemplate.convertAndSend(
                destination,
                model
        );
    }

    private GroupMembershipMessageFromKafka getMembershipMessage(DebeziumOperation operation, String inputMessage) {
        JsonDeserializer<GroupMembershipMessageFromKafka> deserializer = null;
        switch (operation) {
            case CREATE:
                deserializer = deserializerGroupMembershipAccepted;
                break;
            case DELETE:
                deserializer = deserializerLeaveGroup;
                break;
            case UPDATE:
                deserializer = deserializerGroupMembershipAccepted;
                break;
        }
        return deserializer.deserialize(inputMessage);
    }

    private Message getMessageByOperation(DebeziumOperation operation) {
        Message message = null;
        switch (operation) {
            case CREATE:
                message = new AcceptedInviteMessage();
                break;
            case DELETE:
                message = new LeaveGroupMessage();
                break;
            case UPDATE:
                message = new ChangeRoleMessage();
                break;
        }
        return message;
    }
}
