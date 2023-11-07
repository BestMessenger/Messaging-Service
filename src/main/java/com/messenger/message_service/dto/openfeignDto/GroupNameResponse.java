package com.messenger.message_service.dto.openfeignDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupNameResponse {
    private int groupId;
    private String name;
}
