package com.messenger.message_service.openfeign_client;

import com.messenger.message_service.dto.openfeignDto.GroupMembershipResponse;
import com.messenger.message_service.dto.openfeignDto.GroupMembershipResponseForUser;
import com.messenger.message_service.dto.openfeignDto.GroupNameResponse;
import com.messenger.message_service.utils.enums.RoleUserInGroupEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "group-service")
public interface GroupServiceClient {

    @GetMapping("/groups")
    ResponseEntity<List<GroupNameResponse>> getAllgroup();

    @GetMapping("/groups/{groupId}")
    ResponseEntity<GroupNameResponse> getGroupByGroupId(@PathVariable("groupId") int groupId);

    @GetMapping("/group-memberships/user/{userId}")
    ResponseEntity<List<GroupMembershipResponseForUser>> getAllGroupsByUserId(@PathVariable Long userId);

    @GetMapping("/groups/{groupId}/users")
    ResponseEntity<List<GroupMembershipResponseForUser>> getAllUsersByGroupId(@PathVariable Long groupId);

    @DeleteMapping("/group-memberships/user/{userId}/group/{groupId}")
    ResponseEntity<Void> deleteMembershipByGroupIdAndUserId(@PathVariable Long userId, @PathVariable Long groupId);

    @GetMapping("/group-memberships/user/{userId}/group/{groupId}")
    ResponseEntity<GroupMembershipResponse> getGroupMembershipByUserIdAndGroupId(
            @PathVariable Long userId,
            @PathVariable Long groupId);

    @PutMapping("/group-memberships/user/{userId}/group/{groupId}/change-role/{role}")
    GroupMembershipResponse changeRoleUserInGroup(@PathVariable Long userId, @PathVariable Long groupId, @PathVariable RoleUserInGroupEnum role);
}
