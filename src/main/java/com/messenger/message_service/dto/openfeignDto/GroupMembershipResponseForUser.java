package com.messenger.message_service.dto.openfeignDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembershipResponseForUser {
    private Long id;
    private GroupResponse group;
    private Long user;
    private String role;
    private String statusInGroup;
    private LocalDate joinDate;
    private Long offsetMessageId;
}
