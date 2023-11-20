package com.messenger.message_service.utils.jsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.SendMessageFromKafka;
import org.springframework.stereotype.Component;

@Component
public class SendMessageDeserializer extends AbstractMessageDeserializer<SendMessageFromKafka>
implements JsonDeserializer<SendMessageFromKafka> {

    public SendMessageDeserializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public SendMessageFromKafka deserialize(String inputMessageFromKafka) {
        return super.deserialize(inputMessageFromKafka);
    }

    @Override
    protected String getPayloadField() {
        return "after";
    }

    @Override
    protected Class<SendMessageFromKafka> getTargetClass() {
        return SendMessageFromKafka.class;
    }
}