package com.messenger.message_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userid;
    private String username;
    private String password;
    private String contact;
    private String email;
    private String name;
    private String last_name;
}

