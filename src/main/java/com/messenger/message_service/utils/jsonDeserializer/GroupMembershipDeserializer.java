package com.messenger.message_service.utils.jsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.GroupMembershipMessageFromKafka;
import org.springframework.stereotype.Component;

@Component
public class GroupMembershipDeserializer extends AbstractMessageDeserializer<GroupMembershipMessageFromKafka>
implements JsonDeserializer<GroupMembershipMessageFromKafka> {

    public GroupMembershipDeserializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public GroupMembershipMessageFromKafka deserialize(String inputMessageFromKafka) {
        return super.deserialize(inputMessageFromKafka);
    }

    @Override
    protected String getPayloadField() {
        return "after";
    }

    @Override
    protected Class<GroupMembershipMessageFromKafka> getTargetClass() {
        return GroupMembershipMessageFromKafka.class;
    }
}