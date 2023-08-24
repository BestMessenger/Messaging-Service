package com.messenger.message_service.controllers;

import com.google.gson.Gson;
import com.messenger.message_service.dto.*;
import com.messenger.message_service.restclients.GroupService;
import com.messenger.message_service.restclients.RelayService;
import com.messenger.message_service.restclients.UserProfileService;
import com.messenger.message_service.utils.MessageTypeEnum;
import com.messenger.message_service.utils.TransportActionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class WsController {
    private final RestTemplate template;
    private static int messageId;
    private final GroupService groupService;
    private final RelayService relayService;
    private int groupId;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserProfileService userProfileService;

    private final Logger log = LoggerFactory.getLogger(WsController.class);

    @Autowired
    public WsController(RestTemplate template, GroupService groupService, RelayService relayService, SimpMessagingTemplate messagingTemplate, UserProfileService userProfileService) {
        this.template = template;
        this.groupService = groupService;
        this.relayService = relayService;
        this.messagingTemplate = messagingTemplate;
        this.userProfileService = userProfileService;
    }

    @MessageMapping("/message")
    public void mainChannel(InputTransportDTO input) {
        TransportActionEnum action = input.getAction();
        switch (action) {
            case SEND_GROUP_MESSAGE:
                if (groupService.exist(input.getGroupId())) {
                    MessageEntity message = new MessageEntity();
                    message.setMessageId(messageId++);
                    message.setGroupId(input.getGroupId());
                    message.setContent_type(MessageTypeEnum.TEXT.toString());
                    message.setSentFromId(input.getUserId());
                    message.setTimastamp(LocalDateTime.now());
                    message.setMedia_url(input.getMessage());
                    message.setSentToId(input.getUserId());
                    relayService.saveMessageInDatabase(message);
                    log.debug("Сообщение сохранено");

                    OutputTransportDto output = new OutputTransportDto();
                    output.setAction(TransportActionEnum.NOTIFICATION_MESSAGE);

                    List<Integer> toSend = groupService.getAllUserIdFromGroup(input.getGroupId());
                    toSend.forEach(toUserId -> {
                        MessageDTO messageDTO = userProfileService.toMessageDto(message, message.getSentFromId());
                        output.setObject(messageDTO);
                        messagingTemplate.convertAndSend("/topic/user/" + toUserId, output);
                    });
                }
                break;

            case FETCH_GROUP_MESSAGES:
                if (groupService.exist(input.getGroupId())) {
                    List<MessageEntity> messages = relayService.getMessagesByGroupId(input.getGroupId());
                    OutputTransportDto output = new OutputTransportDto(TransportActionEnum.FETCH_GROUP_MESSAGES, messages);
                    messagingTemplate.convertAndSend("/topic/user/" + input.getUserId(), output);
                    log.info("Пользователь получил свои сообщения");
                }
                break;

            case LEAVE_GROUP:
                if (groupService.exist(input.getGroupId())) {
                    groupService.deleteUsetFromGroup(input.getUserId(), input.getGroupId());
                    LeaveGroupDTO leaveGroupDTO = new LeaveGroupDTO();
                    leaveGroupDTO.setGroupName(groupService.getGroupNameByGroupId(input.getGroupId()));
                    OutputTransportDto output = new OutputTransportDto(TransportActionEnum.LEAVE_GROUP, leaveGroupDTO);
                    messagingTemplate.convertAndSend("/topic/user/" + input.getUserId(), output);
                    log.info("Пользователь покинул группу");
                }
                break;
        }
    }

    @MessageMapping("/groups/create/single")
    @SendToUser("/queue/reply")
    public void wsCreateConversation(String payload) {
        Gson gson = new Gson();
        CreateGroupDTO createGroup = gson.fromJson(payload, CreateGroupDTO.class);
        groupService.createGroup(groupId++, createGroup.getName_group());
        groupService.addUserToGroup(createGroup.getUserId1(), groupId);
        groupService.addUserToGroup(createGroup.getUserId2(), groupId);
    }
}