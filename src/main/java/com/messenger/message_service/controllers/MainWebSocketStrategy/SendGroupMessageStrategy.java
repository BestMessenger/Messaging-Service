package com.messenger.message_service.controllers.MainWebSocketStrategy;

import com.messenger.message_service.dto.openfeignDto.UserResponse;
import com.messenger.message_service.dto.websocketDto.InputTransportDTO;
import com.messenger.message_service.dto.websocketDto.OutputTransportDto;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.repository.MessageRepository;
import com.messenger.message_service.utils.enums.MessageTypeEnum;
import com.messenger.message_service.utils.enums.TransportActionEnum;
import com.messenger.message_service.utils.userChecker.UserChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SendGroupMessageStrategy implements ActionStrategy, DefaultOperationWithMessages<UserResponse> {
    private final MessageRepository messageRepository;
    private final UserChecker<UserResponse> userChecker;

    @Override
    public OutputTransportDto execute(InputTransportDTO inputTransportDTO) {
        UserResponse user = checkUserInDatabase(inputTransportDTO.getSender_id());

        if (user != null) {
            MessageModel model = MessageModel.builder()
                    .messageType(MessageTypeEnum.TEXT_MESSAGE)
                    .message(inputTransportDTO.getMessage()) //todo add env in docker
                    .groupId(inputTransportDTO.getGroup_id())
                    .senderId(inputTransportDTO.getSender_id())
                    .sendDatetime(LocalDateTime.now())
                    .username(user.getUsername())
                    .build();
            saveMessageInDatabase(model);

            OutputTransportDto outputMessage = new OutputTransportDto();
            outputMessage.setDestination("/group/" + inputTransportDTO.getGroup_id());
            outputMessage.setMessage(model);

            return outputMessage;
        }
        return new OutputTransportDto(
                "/error/" + inputTransportDTO.getSender_id(),
                "User with id " + inputTransportDTO.getSender_id() + " not exist"
        );
    }

    @Override
    public TransportActionEnum getType() {
        return TransportActionEnum.SEND_GROUP_MESSAGE;
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
