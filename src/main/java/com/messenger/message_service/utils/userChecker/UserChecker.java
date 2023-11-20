package com.messenger.message_service.utils.userChecker;

public interface UserChecker<User> {
    User isExistUserInProfileService(Long userId, Long groupId);
}
