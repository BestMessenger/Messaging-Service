package com.messenger.message_service.utils.checkOperation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.message_service.utils.enums.DebeziumOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckOperation {

    private final ObjectMapper objectMapper;

    public DebeziumOperation deserialize(String inputMessageFromKafka) {
        try {
            String op = objectMapper.readTree(inputMessageFromKafka).path("payload").path("op").asText();

            switch (op) {
                case "c":
                    return DebeziumOperation.CREATE;
                case "d":
                    return DebeziumOperation.DELETE;
                case "u":
                    return DebeziumOperation.UPDATE;
                default:
                    throw new RuntimeException("Unknown operation: " + op);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
