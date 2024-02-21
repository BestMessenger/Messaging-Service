package com.messenger.message_service.controllers;

import com.messenger.message_service.dto.websocketDto.MessageRequest;
import com.messenger.message_service.dto.websocketDto.MessageResponse;
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
    public ResponseEntity<Long> getMaxMessageId(@PathVariable Long groupId) {
        Long maxMessageId = messageService.getMaxMessageId(groupId);

        if (maxMessageId != null) {
            return ResponseEntity.ok(maxMessageId);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Long> getMinMessageId(@PathVariable Long groupId) {
        Long minMessageId = messageService.getMinMessageId(groupId);

        if (minMessageId != null) {
            return ResponseEntity.ok(minMessageId);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<List<MessageResponse>> getMessagesByGroupIdOrderByDateTime(@PathVariable Long groupId) {
        List<MessageResponse> messages = messageService.getMessagesByGroupId(groupId);

        if (!messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        List<MessageResponse> groupMessages = messageService.getGroupMessagesByUserIdAndGroupId(userId, groupId);

        if (!groupMessages.isEmpty()) {
            return ResponseEntity.ok(groupMessages);
        } else {
            return ResponseEntity.notFound().build();
        }
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
            @RequestBody MessageRequest request) {
        MessageResponse createdMessage = messageService.createMessage(request);

        if (createdMessage != null) {
            return ResponseEntity.ok(createdMessage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
