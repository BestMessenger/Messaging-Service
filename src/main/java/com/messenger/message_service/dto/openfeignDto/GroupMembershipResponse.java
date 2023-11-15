package com.messenger.message_service.dto.openfeignDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembershipResponse {
    private Long id;
    private Long group_id;
    private Long user_id;
    private String role;
    private String status_in_group;
}
