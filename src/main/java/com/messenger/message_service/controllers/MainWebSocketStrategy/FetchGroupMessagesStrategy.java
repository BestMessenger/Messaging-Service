package com.messenger.message_service.controllers.MainWebSocketStrategy;

import com.messenger.message_service.dto.openfeignDto.GroupMembershipResponse;
import com.messenger.message_service.dto.openfeignDto.UserResponse;
import com.messenger.message_service.dto.websocketDto.InputTransportDTO;
import com.messenger.message_service.dto.websocketDto.OutputTransportDto;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.openfeign_client.GroupServiceClient;
import com.messenger.message_service.repository.MessageRepository;
import com.messenger.message_service.utils.enums.MessageTypeEnum;
import com.messenger.message_service.utils.enums.TransportActionEnum;
import com.messenger.message_service.utils.userChecker.UserChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class FetchGroupMessagesStrategy implements ActionStrategy, DefaultOperationWithMessages<UserResponse> {
    private final GroupServiceClient groupServiceClient;
    private final UserChecker<UserResponse> userChecker;
    private final MessageRepository messageRepository;

    @Override
    public OutputTransportDto execute(InputTransportDTO inputTransportDTO) {
        UserResponse user = checkUserInDatabase(inputTransportDTO.getSender_id());

        if (user != null) {
            GroupMembershipResponse response = groupServiceClient.getGroupMembershipByUserIdAndGroupId(
                    inputTransportDTO.getSender_id(),
                    inputTransportDTO.getGroup_id()
            ).getBody();

            List<MessageModel> messages = messageRepository.searchAllMessagesInGroupStartFromOffset(
                    inputTransportDTO.getGroup_id(), response.getOffset_message_id());

            MessageModel model = MessageModel.builder()
                    .messageType(MessageTypeEnum.FETCH_MESSAGES)
                    .message(user.getUsername() + " fetch messages") //todo add env in docker
                    .groupId(inputTransportDTO.getGroup_id())
                    .senderId(inputTransportDTO.getSender_id())
                    .sendDatetime(LocalDateTime.now())
                    .username(user.getUsername())
                    .build();
            saveMessageInDatabase(model);

            OutputTransportDto outputMessage = new OutputTransportDto();
            outputMessage.setDestination("/group/" + inputTransportDTO.getGroup_id());
            outputMessage.setMessage(messages);

            return outputMessage;
        }
        return new OutputTransportDto(
                "/error" + inputTransportDTO.getSender_id(),
                "User with id" + inputTransportDTO.getSender_id() + " not exist"
        );
    }

    @Override
    public TransportActionEnum getType() {
        return TransportActionEnum.FETCH_GROUP_MESSAGES;
    }

    @Override
    public void saveMessageInDatabase(MessageModel model) {
        messageRepository.save(model);
    }

    @Override
    public UserResponse checkUserInDatabase(Long userId) {
        return userChecker.isExistUserInProfileService(userId);
    }
}
