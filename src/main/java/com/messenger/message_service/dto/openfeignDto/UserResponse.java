package com.messenger.message_service.dto.openfeignDto;

import lombok.Data;

@Data
public class UserResponse {
    private Long userid;
    private String username;
    private String password;
    private String contact;
    private String email;
    private String name;
    private String last_name;
}
