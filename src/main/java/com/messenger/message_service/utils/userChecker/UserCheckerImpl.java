package com.messenger.message_service.utils.userChecker;

import com.messenger.message_service.dto.openfeignDto.UserResponse;
import com.messenger.message_service.exception.NoEntityFoundException;
import com.messenger.message_service.openfeign_client.UserProfileServiceClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserCheckerImpl implements UserChecker<UserResponse> {

    private final UserProfileServiceClient userProfileServiceClient;

    @Override
    public UserResponse isExistUserInProfileService(Long userId, Long groupId) {
        try {
            return userProfileServiceClient.getUserById(userId);
        } catch (FeignException.NotFound e) {
            return null;
        }
    }
}
