package com.messenger.message_service.service;

import com.messenger.message_service.exception.NoEntityFoundException;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Long findMaxIdByGroup(Long groupId) {
        return messageRepository.findMaxIdByGroup(groupId);
    }

    public Long findMinIdByGroup(Long groupId) {
        return messageRepository.findMinIdByGroup(groupId);
    }

    public List<MessageModel> getMessagesByGroupId(Long groupId) {
        return messageRepository.findAllByGroupIdOrderBySendDatetimeAsc(groupId);
    }
}
