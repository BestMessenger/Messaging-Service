package com.messenger.message_service.controllers;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MainWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

}
