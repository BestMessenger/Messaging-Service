package com.messenger.message_service.openfeign_client;

import com.messenger.message_service.dto.openfeignDto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserProfileServiceClient {

    @GetMapping("/user/{userid}")
    UserResponse getUserById(@PathVariable("userid") Long userid);
}
