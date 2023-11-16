package com.messenger.message_service.controllers;

import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Get the maximum message ID in a group",
            description = "Retrieve the maximum message ID in a group based on the group ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the maximum message ID"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public Long getMaxMessageId(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        return messageService.findMaxIdByGroup(groupId);
    }

    @GetMapping("/minId/{groupId}")
    @Operation(
            summary = "Get the minimum message ID in a group",
            description = "Retrieve the minimum message ID in a group based on the group ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the minimum message ID"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public Long getMinMessageId(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        return messageService.findMinIdByGroup(groupId);
    }

    @GetMapping("/group/{groupId}")
    @Operation(
            summary = "Get messages in a group",
            description = "Retrieve messages in a group based on the group ID, ordered by send datetime."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved messages in the group"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public List<MessageModel> getMessagesByGroupIdOrderByDateTime(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        return messageService.getMessagesByGroupId(groupId);
    }
}
