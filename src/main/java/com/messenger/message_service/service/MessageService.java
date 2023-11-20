package com.messenger.message_service.service;

import com.messenger.message_service.dto.dtoMapper.DtoMapper;
import com.messenger.message_service.dto.openfeignDto.GroupMembershipResponse;
import com.messenger.message_service.dto.websocketDto.MessageRequest;
import com.messenger.message_service.dto.websocketDto.MessageResponse;
import com.messenger.message_service.exception.NoEntityFoundException;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.repository.MessageRepository;
import com.messenger.message_service.utils.userChecker.UserChecker;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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

    public MessageResponse createMessage(MessageRequest messageRequest) {
        MessageModel messageModel = dtoMapper.toModel(messageRequest);
        GroupMembershipResponse membershipResponse = responseUserGroupChecker.isExistUserInProfileService(
                messageRequest.getSenderId(), messageRequest.getGroupId());
        if (membershipResponse != null) {
            MessageModel savedMessage = messageRepository.save(messageModel);
            return dtoMapper.toResponse(savedMessage);
        }
        throw new NoEntityFoundException("the user " + messageModel.getSenderId() + " is not a member of group " + messageModel.getGroupId() + " or does not exist");
    }
}
