package com.messenger.message_service.controllers;

import com.messenger.message_service.controllers.MainWebSocketStrategy.ActionStrategyFactory;
import com.messenger.message_service.dto.websocketDto.InputTransportDTO;
import com.messenger.message_service.dto.websocketDto.OutputTransportDto;
import com.messenger.message_service.utils.enums.TransportActionEnum;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MainWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ActionStrategyFactory strategyFactory;

    @MessageMapping("/message")
    public void mainChannel(InputTransportDTO inputTransportDTO) {
        TransportActionEnum action = inputTransportDTO.getAction();
        OutputTransportDto outputTransportDto = strategyFactory.executeStrategy(action, inputTransportDTO);
        simpMessagingTemplate.convertAndSend(
                outputTransportDto.getDestination(),
                outputTransportDto.getMessage()
        );
    }
}
