package com.messenger.message_service.utils.MessageFactory;

import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.utils.enums.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Component
public class ChangeRoleMessage implements Message {
    @Override
    public MessageModel createMessage(String message, Long userId, Long groupId, String fileUri) {
        return MessageModel.builder()
                .messageType(MessageTypeEnum.CHANGE_ROLE_MESSAGE)
                .message(message)
                .groupId(groupId)
                .senderId(userId)
                .sendDatetime(LocalDateTime.now())
                .fileUrl(null)
                .build();
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.CHANGE_ROLE_MESSAGE;
    }
}
