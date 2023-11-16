// MessageController.java
package com.messenger.message_service.controllers;

import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/maxId/{groupId}")
    public Long getMaxMessageId(@PathVariable Long groupId) {
        return messageService.findMaxIdByGroup(groupId);
    }

    @GetMapping("/minId/{groupId}")
    public Long getMinMessageId(@PathVariable Long groupId) {
        return messageService.findMinIdByGroup(groupId);
    }

    @GetMapping("/group/{groupId}")
    public List<MessageModel> getMessagesByGroupIdOrderByDateTime(@PathVariable Long groupId) {
        return messageService.getMessagesByGroupId(groupId);
    }
}
