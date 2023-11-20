package com.messenger.message_service.utils.userChecker;

import com.messenger.message_service.dto.openfeignDto.GroupMembershipResponse;
import com.messenger.message_service.exception.NoEntityFoundException;
import com.messenger.message_service.openfeign_client.GroupServiceClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCheckerInGroupImp implements UserChecker<GroupMembershipResponse> {
    private final GroupServiceClient groupServiceClient;

    @Override
    public GroupMembershipResponse isExistUserInProfileService(Long userId, Long groupId) {
        try {
            return groupServiceClient.getGroupMembershipByUserIdAndGroupId(userId, groupId).getBody();
        } catch (FeignException.NotFound e) {
            throw new NoEntityFoundException("the user " + userId + " is not a member of group " + groupId + " or does not exist");
        }
    }
}
