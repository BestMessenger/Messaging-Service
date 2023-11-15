package com.messenger.message_service.controllers.MainWebSocketStrategy;

import com.messenger.message_service.dto.websocketDto.InputTransportDTO;
import com.messenger.message_service.dto.websocketDto.OutputTransportDto;
import com.messenger.message_service.utils.enums.TransportActionEnum;
import org.springframework.stereotype.Component;

@Component
public class FetchGroupMessagesStrategy implements ActionStrategy {
    @Override
    public OutputTransportDto execute(InputTransportDTO inputTransportDTO) {

        return null;
    }

    @Override
    public TransportActionEnum getType() {
        return TransportActionEnum.FETCH_GROUP_MESSAGES;
    }
}
