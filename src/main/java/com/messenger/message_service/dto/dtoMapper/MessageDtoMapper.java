package com.messenger.message_service.dto.dtoMapper;

import com.messenger.message_service.dto.websocketDto.MessageRequest;
import com.messenger.message_service.dto.websocketDto.MessageResponse;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.utils.enums.MessageTypeEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageDtoMapper implements DtoMapper<MessageModel, MessageRequest, MessageResponse> {

    @Override
    public MessageModel toModel(MessageRequest messageRequest) {
        return MessageModel.builder()
                .messageType(MessageTypeEnum.TEXT_MESSAGE)
                .message(messageRequest.getMessage())
                .groupId(messageRequest.getGroupId())
                .senderId(messageRequest.getSenderId())
                .sendDatetime(LocalDateTime.now())
                .fileUrl(null)
                .build();
    }

    @Override
    public MessageResponse toResponse(MessageModel messageModel) {
        return MessageResponse.builder()
                .id(messageModel.getId())
                .messageType(messageModel.getMessageType())
                .message(messageModel.getMessage())
                .groupId(messageModel.getGroupId())
                .senderId(messageModel.getSenderId())
                .sendDatetime(messageModel.getSendDatetime())
                .fileUrl(messageModel.getFileUrl())
                .build();
    }

    @Override
    public List<MessageResponse> toResponseList(List<MessageModel> messageModels) {
        return messageModels.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
