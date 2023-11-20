package com.messenger.message_service.dto.openfeignDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupResponse {
    private int group_id;

    private int user_id;

    private String role;
}
