package com.messenger.message_service.utils.jsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.InviteMessageFromKafka;
import org.springframework.stereotype.Component;

@Component
public class InviteMessageDeserializer extends AbstractMessageDeserializer<InviteMessageFromKafka>
implements JsonDeserializer<InviteMessageFromKafka> {

    @Override
    public InviteMessageFromKafka deserialize(String inputMessageFromKafka) {
        return super.deserialize(inputMessageFromKafka);
    }

    public InviteMessageDeserializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected String getPayloadField() {
        return "after";
    }

    @Override
    protected Class<InviteMessageFromKafka> getTargetClass() {
        return InviteMessageFromKafka.class;
    }
}