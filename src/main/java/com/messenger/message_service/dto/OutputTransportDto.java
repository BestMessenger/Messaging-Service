package com.messenger.message_service.dto;

import com.messenger.message_service.utils.TransportActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputTransportDto {

    private TransportActionEnum action;

    private Object object;
}
