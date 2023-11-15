package com.messenger.message_service.controllers.MainWebSocketStrategy;

import com.messenger.message_service.models.MessageModel;

public interface DefaultOperationWithMessages<User> {
    void saveMessageInDatabase(MessageModel model);
    User checkUserInDatabase(Long userId);
}
