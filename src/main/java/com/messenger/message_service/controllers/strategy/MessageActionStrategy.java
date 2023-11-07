package com.messenger.message_service.controllers.strategy;

import com.messenger.message_service.dto.InputTransportDTO;

public interface MessageActionStrategy {
    void execute(InputTransportDTO input);
}
