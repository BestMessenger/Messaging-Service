package com.messenger.message_service.openfeign_client;

import com.messenger.message_service.dto.openfeignDto.GroupNameResponse;
import com.messenger.message_service.dto.openfeignDto.GroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "group-service")
public interface GroupServiceClient {

    @GetMapping("/groups")
    ResponseEntity<List<GroupNameResponse>> getAllgroup();

    @GetMapping("/groups/{groupId}")
    ResponseEntity<GroupNameResponse> getGroupByGroupId(@PathVariable("groupId") int groupId);

    @GetMapping("/membership/group-id/{groupId}")
    ResponseEntity<List<GroupResponse>> getAllUsersByGroupId(@PathVariable("groupId") int groupId);

    @DeleteMapping("/membership/{userId}/{groupId}")
    ResponseEntity<Object> deleteUserFromGroup(@PathVariable("userId") int userId, @PathVariable("groupId") int groupId);

    @GetMapping("/groups/{groupId}")
    ResponseEntity<GroupNameResponse> getGroupNameById(@PathVariable("groupId") int groupId);
}
