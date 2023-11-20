package com.messenger.message_service.dto.websocketDto;

import com.messenger.message_service.utils.enums.TransportActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputTransportDTO {
    private TransportActionEnum action;
    private Long sender_id;
    private Long group_id;
    private String message;
}
