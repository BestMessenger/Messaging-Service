package com.messenger.message_service.controllers;

import com.messenger.message_service.dto.websocketDto.MessageRequest;
import com.messenger.message_service.dto.websocketDto.MessageResponse;
import com.messenger.message_service.models.MessageModel;
import com.messenger.message_service.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/max-id/{groupId}")
    @Operation(
            summary = "Get the maximum message ID in a group",
            description = "Retrieve the maximum message ID in a group based on the group ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the maximum message ID"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public ResponseEntity<Long> getMaxMessageId(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        return ResponseEntity.ok(messageService.findMaxIdByGroup(groupId));
    }


    @GetMapping("/min-id/{groupId}")
    @Operation(
            summary = "Get the minimum message ID in a group",
            description = "Retrieve the minimum message ID in a group based on the group ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the minimum message ID"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    public ResponseEntity<Long> getMinMessageId(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        return ResponseEntity.ok(messageService.findMinIdByGroup(groupId));
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
    public ResponseEntity<List<MessageResponse>> getMessagesByGroupIdOrderByDateTime(@Parameter(description = "Group ID", required = true) @PathVariable Long groupId) {
        return ResponseEntity.ok(messageService.getMessagesByGroupId(groupId));
    }


    @GetMapping("/fetch-group-messages/user-id/{userId}/group-id/{groupId}")
    @Operation(
            summary = "Fetch group messages",
            description = "Fetch messages in a group based on the provided user and group IDs.",
            parameters = {
                    @Parameter(name = "userId", description = "User ID", required = true),
                    @Parameter(name = "groupId", description = "Group ID", required = true)
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved group messages"),
            @ApiResponse(responseCode = "404", description = "Group not found or user not a member")
    })
    public ResponseEntity<List<MessageResponse>> fetchGroupMessages(@PathVariable Long userId, @PathVariable Long groupId) {
        return ResponseEntity.ok(messageService.getGroupMessagesByUserIdAndGroupId(userId, groupId));
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new message",
            description = "Creates and saves a new message in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message created successfully"),
            @ApiResponse(responseCode = "404", description = "User is not a member of the group or does not exist")
    })
    public ResponseEntity<MessageResponse> createMessage(
            @Parameter(description = "Message details", required = true)
            @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.createMessage(request));
    }
}
