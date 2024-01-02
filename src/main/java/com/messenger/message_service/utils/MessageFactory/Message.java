package com.messenger.message_service.utils.MessageFactory;

import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.utils.enums.MessageTypeEnum;

public interface Message {
    MessageModel createMessage(String message, Long userId, Long groupId, String fileUri);

    MessageTypeEnum getType();
}
