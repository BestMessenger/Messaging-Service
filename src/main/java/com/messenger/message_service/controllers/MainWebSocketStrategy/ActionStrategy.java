package com.messenger.message_service.controllers.MainWebSocketStrategy;

import com.messenger.message_service.dto.websocketDto.InputTransportDTO;
import com.messenger.message_service.dto.websocketDto.OutputTransportDto;
import com.messenger.message_service.utils.enums.TransportActionEnum;

public interface ActionStrategy {
    OutputTransportDto execute(InputTransportDTO inputTransportDTO);
    TransportActionEnum getType();
}
