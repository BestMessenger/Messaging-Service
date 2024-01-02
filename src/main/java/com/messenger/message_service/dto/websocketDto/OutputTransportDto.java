package com.messenger.message_service.dto.websocketDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputTransportDto {
    private String destination;
    private Object message;
}
