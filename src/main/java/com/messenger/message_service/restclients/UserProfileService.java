package com.messenger.message_service.restclients;

import com.messenger.message_service.dto.MessageDTO;
import com.messenger.message_service.dto.MessageEntity;
import com.messenger.message_service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserProfileService {

    @Value("${userProfile-service-url}")
    private String url;

    private UserProfileService userProfileService;

    @Autowired
    private RestTemplate restTemplate;

    public UserDTO getUserById (int userId) {
        return restTemplate.getForObject(url + "/" + userId, UserDTO.class);
    }

    public MessageDTO toMessageDto (MessageEntity message, int userId) {
        UserDTO user = userProfileService.getUserById(userId);


        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getMessageId());
        messageDTO.setType(message.getContent_type());
        messageDTO.setMessage(messageDTO.getMessage());
        messageDTO.setUserId(messageDTO.getUserId());
        messageDTO.setGroupId(messageDTO.getGroupId());
        messageDTO.setSender(user.getName());
        messageDTO.setTime(messageDTO.getTime());
        messageDTO.setInitials(user.getUsername());
        messageDTO.setFileUrl(messageDTO.getFileUrl());
        return messageDTO;

    }
}
