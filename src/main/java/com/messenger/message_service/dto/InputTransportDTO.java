package com.messenger.message_service.dto;

import com.messenger.message_service.utils.TransportActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputTransportDTO {
    private int userId;
    private TransportActionEnum action;
    private int groupId;
    private String message;
}
