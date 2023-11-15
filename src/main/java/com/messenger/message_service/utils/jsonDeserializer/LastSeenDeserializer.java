package com.messenger.message_service.utils.jsonDeserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.LastSeenMessageFromKafka;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LastSeenDeserializer implements JsonDeserializer<LastSeenMessageFromKafka> {

    private final ObjectMapper objectMapper;

    @Override
    public LastSeenMessageFromKafka deserialize(String inputMessageFromKafka) {
        try {
            JsonNode lastSeenNode = objectMapper.readTree(inputMessageFromKafka).path("payload").path("after");
            return objectMapper.treeToValue(lastSeenNode, LastSeenMessageFromKafka.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
