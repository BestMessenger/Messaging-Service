package com.messenger.message_service.service;

import com.messenger.message_service.dto.dtoMapper.DtoMapper;
import com.messenger.message_service.dto.openfeignDto.GroupMembershipResponse;
import com.messenger.message_service.dto.websocketDto.MessageRequest;
import com.messenger.message_service.dto.websocketDto.MessageResponse;
import com.messenger.message_service.encryption.MessageEncryptor;
import com.messenger.message_service.exception.NoEntityFoundException;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.repository.MessageRepository;
import com.messenger.message_service.utils.userChecker.UserChecker;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final UserChecker<GroupMembershipResponse> responseUserGroupChecker;
    private final MessageRepository messageRepository;
    private final DtoMapper<MessageModel, MessageRequest, MessageResponse> dtoMapper;

    public Long findMaxIdByGroup(Long groupId) {
        return messageRepository.findMaxIdByGroup(groupId);
    }

    public Long findMinIdByGroup(Long groupId) {
        return messageRepository.findMinIdByGroup(groupId);
    }

    public Long getMaxMessageId(Long groupId) {
        return messageRepository.findMaxIdByGroup(groupId);
    }

    public Long getMinMessageId(Long groupId) {
        return messageRepository.findMinIdByGroup(groupId);
    }

    public List<MessageResponse> getMessagesByGroupId(Long groupId) {
        List<MessageModel> messageModels = messageRepository.findAllByGroupIdOrderBySendDatetimeAsc(groupId);
        return dtoMapper.toResponseList(messageModels);
    }

    public List<MessageResponse> getGroupMessagesByUserIdAndGroupId(Long userId, Long groupId) {
        GroupMembershipResponse membershipResponse = responseUserGroupChecker.isExistUserInProfileService(
                userId, groupId);
        List<MessageModel> messageModels = messageRepository.searchAllMessagesInGroupStartFromOffset(
                groupId, membershipResponse.getOffset_message_id());
        return dtoMapper.toResponseList(messageModels);
    }


    @SneakyThrows
    public MessageResponse createMessage(MessageRequest messageRequest) {
        MessageModel messageModel = MessageModel.builder()
                .message(messageRequest.getMessage())
                .groupId(messageRequest.getGroupId())
                .senderId(messageRequest.getSenderId())
                .sendDatetime(LocalDateTime.now())
                .build();

        byte[] key;
        if (messageRequest.getGroupId() != null) {
            key = MessageEncryptor.generateKey();
        } else {
            key = MessageEncryptor.generateKeyFromMessageAndSenderId(
                    messageRequest.getMessage(), String.valueOf(messageRequest.getSenderId()));
        }

        byte[] encryptedMessage = MessageEncryptor.encrypt(key, messageRequest.getMessage(), String.valueOf(messageRequest.getSenderId()));

        String encryptedMessageBase64 = Base64.getEncoder().encodeToString(encryptedMessage);
        messageModel.setMessage(encryptedMessageBase64);

        MessageModel savedMessage = messageRepository.save(messageModel);

        return dtoMapper.toResponse(savedMessage);
    }

    @SneakyThrows
    public String getDecryptedMessage(Long messageId){
        MessageModel messageModel = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + messageId));
        byte[] key;
        if (messageModel.getGroupId() != null) {
            key = MessageEncryptor.generateKey();
        } else {
            key = MessageEncryptor.generateKeyFromMessageAndSenderId(
                    messageModel.getMessage(), String.valueOf(messageModel.getSenderId()));
        }

        String decryptedMessage = MessageEncryptor.decrypt(key, messageModel.getMessage());

        return decryptedMessage;
    }

}
