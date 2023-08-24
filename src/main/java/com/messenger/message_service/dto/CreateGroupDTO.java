package com.messenger.message_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDTO {
    private int userId1;
    private int userId2;
    private String name_group;
}
