package com.messenger.message_service.utils.MessageFactory;

import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.utils.enums.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Component
public class InviteToGroupMessage implements Message {
    private String invitationMessage;

    @Override
    public MessageModel createMessage(String message, Long userId, Long groupId, String fileUri) {
        return MessageModel.builder()
                .messageType(MessageTypeEnum.INVITE_TO_GROUP_MESSAGE)
                .message(invitationMessage)
                .groupId(groupId)
                .senderId(userId)
                .sendDatetime(LocalDateTime.now())
                .fileUrl(fileUri)
                .build();
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.INVITE_TO_GROUP_MESSAGE;
    }
}
