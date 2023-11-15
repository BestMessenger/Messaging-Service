package com.messenger.message_service.controllers.MainWebSocketStrategy;

import com.messenger.message_service.dto.websocketDto.InputTransportDTO;
import com.messenger.message_service.dto.websocketDto.OutputTransportDto;
import com.messenger.message_service.utils.enums.TransportActionEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ActionStrategyFactory {

    private final Map<TransportActionEnum, ActionStrategy> strategyMap;

    @Autowired
    public ActionStrategyFactory(List<ActionStrategy> actionStrategies) {
        this.strategyMap = actionStrategies
                .stream()
                .collect(Collectors.toMap(ActionStrategy::getType, Function.identity()));
    }

    public OutputTransportDto executeStrategy(TransportActionEnum action, InputTransportDTO inputTransportDTO) {
        ActionStrategy strategy = strategyMap.get(action);
        if (strategy != null) {
            return strategy.execute(inputTransportDTO);
        }
        throw new IllegalArgumentException("Unsupported action: " + action);
    }
}
