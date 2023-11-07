package com.messenger.message_service.openfeign_client;

import com.messenger.message_service.dto.MessageEntity;
import com.messenger.message_service.dto.openfeignDto.MessageRequestDTO;
import com.messenger.message_service.dto.openfeignDto.MessageResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "relay-service")
public interface RelayServiceClient {

    @PostMapping(name = "/messages")
    ResponseEntity<MessageEntity> saveMessage(@RequestBody MessageEntity requestDTO);

    @GetMapping("/messages/{group_id}")
    ResponseEntity<List<MessageEntity>> getMessagesByGroupId(@PathVariable("group_id") int groupId);
}
