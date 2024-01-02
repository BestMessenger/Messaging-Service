package com.messenger.message_service.utils.jsonDeserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMessageDeserializer<T> implements JsonDeserializer<T> {
    protected final ObjectMapper objectMapper;

    public AbstractMessageDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T deserialize(String inputMessageFromKafka) {
        try {
            JsonNode node = objectMapper.readTree(inputMessageFromKafka).path("payload").path(getPayloadField());
            return objectMapper.treeToValue(node, getTargetClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getPayloadField();

    protected abstract Class<T> getTargetClass();
}
