package com.messenger.message_service.controllers.strategy;

import com.messenger.message_service.dto.InputTransportDTO;
import com.messenger.message_service.dto.LeaveGroupDTO;
import com.messenger.message_service.dto.OutputTransportDto;
import com.messenger.message_service.openfeign_client.GroupServiceClient;
import com.messenger.message_service.openfeign_client.RelayServiceClient;
import com.messenger.message_service.utils.TransportActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LeaveGroupStrategy implements MessageActionStrategy {

    private final GroupServiceClient groupServiceClient;
    private final RelayServiceClient relayServiceClient;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public LeaveGroupStrategy(GroupServiceClient groupServiceClient, RelayServiceClient relayServiceClient, SimpMessagingTemplate messagingTemplate) {
        this.groupServiceClient = groupServiceClient;
        this.relayServiceClient = relayServiceClient;
        this.messagingTemplate = messagingTemplate;
    }
    @Override
    public void execute(InputTransportDTO input) {
        if (groupServiceClient.getGroupByGroupId(input.getGroupId()).getBody() != null) {
            groupServiceClient.deleteUserFromGroup(input.getUserId(), input.getGroupId());
            LeaveGroupDTO leaveGroupDTO = new LeaveGroupDTO();
            leaveGroupDTO.setGroupName(groupServiceClient.getGroupNameById(input.getGroupId()).getBody().getName());
            OutputTransportDto output = new OutputTransportDto(TransportActionEnum.LEAVE_GROUP, leaveGroupDTO);
            messagingTemplate.convertAndSend("/topic/user/" + input.getUserId(), output);
        }
    }
}
