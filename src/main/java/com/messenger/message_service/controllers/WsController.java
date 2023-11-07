package com.messenger.message_service.controllers;

import com.messenger.message_service.controllers.strategy.FetchGroupMessagesStrategy;
import com.messenger.message_service.controllers.strategy.LeaveGroupStrategy;
import com.messenger.message_service.controllers.strategy.MessageActionStrategy;
import com.messenger.message_service.controllers.strategy.SendGroupMessageStrategy;
import com.messenger.message_service.dto.*;
import com.messenger.message_service.openfeign_client.GroupServiceClient;
import com.messenger.message_service.openfeign_client.RelayServiceClient;
import com.messenger.message_service.utils.TransportActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class WsController {

    /*
     * todo Создать логику для групповых чатиков
     * todo создать логику для отправок сообщений
     * todo создать логику для отписок от топиков
     * todo создать логику для отправки медиафайлов
     * todo Отправка документов и файлов
     * todo Эмодзи и стикеры
     * todo логика что сообщение прочитанное или нет
     * todo переделать rest template на openfeign
     * todo уведомления на почту о новом сообщении в группе
     * todo сделать настройку что человек может замутить акканут
     * todo поиск сообщений и файлов
     *
     *
     * todo переделать userprofile-service
     * todo переделать session service
     *
     * Group
     * отправить сообщения
     * получить все сообщения начиная с оффсета человека
     * отправка медиа файлов и эмодзи
     * покинуть группу
     * пригласить человека в группу
     * закрепленные сообщения
     * смена прав администратора и смена администратора
     * создание и подписка на новую группу
     * */

    public static int messageId; // todo исправить
    private final Map<TransportActionEnum, MessageActionStrategy> strategyMap;

    private final SimpMessagingTemplate messagingTemplate;
    private final GroupServiceClient groupServiceClient;
    private final RelayServiceClient relayServiceClient;

    @Autowired
    public WsController(SimpMessagingTemplate messagingTemplate, GroupServiceClient groupServiceClient, RelayServiceClient relayServiceClient) {
        this.relayServiceClient = relayServiceClient;
        this.messagingTemplate = messagingTemplate;
        this.groupServiceClient = groupServiceClient;

        // Initialize the strategy map
        this.strategyMap = new HashMap<>();
        strategyMap.put(TransportActionEnum.SEND_GROUP_MESSAGE, new SendGroupMessageStrategy(groupServiceClient, relayServiceClient, messagingTemplate));
        strategyMap.put(TransportActionEnum.FETCH_GROUP_MESSAGES, new FetchGroupMessagesStrategy(groupServiceClient, relayServiceClient, messagingTemplate));
        strategyMap.put(TransportActionEnum.LEAVE_GROUP, new LeaveGroupStrategy(groupServiceClient, relayServiceClient, messagingTemplate));
    }

    @MessageMapping("/message")
    public void mainChannel(InputTransportDTO input) {
        TransportActionEnum action = input.getAction();
        MessageActionStrategy strategy = strategyMap.get(action);

        if (strategy != null) {
            strategy.execute(input);
        }
    }
}

