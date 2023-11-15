package com.messenger.message_service.utils.jsonDeserializer;

public interface JsonDeserializer<ObjectForResponse> {
    ObjectForResponse deserialize(String inputMessageFromKafka);
}
