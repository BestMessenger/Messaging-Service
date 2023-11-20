package com.messenger.message_service.utils.jsonDeserializer;

import org.springframework.stereotype.Component;

@Component
public interface JsonDeserializer<ObjectForResponse> {
    ObjectForResponse deserialize(String inputMessageFromKafka);
}
