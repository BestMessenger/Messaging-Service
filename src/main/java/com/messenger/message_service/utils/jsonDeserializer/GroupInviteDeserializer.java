package com.messenger.message_service.utils.jsonDeserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.message_service.utils.inputMessageFromKafkaTemplate.GroupInviteMessageFromKafka;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GroupInviteDeserializer implements JsonDeserializer<GroupInviteMessageFromKafka> {

    private final ObjectMapper objectMapper;

    @Override
    public GroupInviteMessageFromKafka deserialize(String inputMessageFromKafka) {
        try {
            JsonNode groupInviteNode = groupInviteNode = objectMapper.readTree(inputMessageFromKafka).path("payload").path("after");
            return objectMapper.treeToValue(groupInviteNode, GroupInviteMessageFromKafka.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
