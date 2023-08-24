package com.messenger.message_service.restclients;

import com.messenger.message_service.dto.GroupDTO;
import com.messenger.message_service.dto.GroupNameServiceResponse;
import com.messenger.message_service.dto.GroupServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class GroupService {

    @Value("${http://localhost:9000/group-service}")
    private String url;

    @Autowired
    RestTemplate restTemplate;


    public List<Integer> getAllUserIdFromGroup(int groupId) {
        List<GroupDTO> groupDTOS = restTemplate.exchange(
                url + "/membership/group-id/" + groupId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GroupDTO>>() {
                }
        ).getBody();

        List<Integer> userIds = groupDTOS.stream()
                .map(GroupDTO::getUser_id)
                .collect(Collectors.toList());
        return userIds;
    }

    public void addUserToGroup(int userId, int groupId) {
        restTemplate.postForObject(
                url + "/membership/" + userId + "/" + groupId,
                null,
                String.class);
    }

    public void deleteUsetFromGroup(int userId, int groupId) {
        restTemplate.delete(url + "/membership/" + userId + "/" + groupId);
    }

    public void createGroup(int groupId, String nameGroup) {
        GroupServiceRequest request = new GroupServiceRequest(groupId, nameGroup);
        restTemplate.postForObject(
                url + "/groups",
                request,
                String.class);
    }

    public void deleteGroup(int groupId) {
        restTemplate.delete(url + "/groups/" + groupId);
    }

    public boolean exist(int groupId) {
        GroupDTO groupDto = restTemplate.getForObject(url + "/groups/" + groupId, GroupDTO.class);
        return groupDto != null;
    }

    public String getGroupNameByGroupId(int groupId) {
        GroupNameServiceResponse response = restTemplate.getForObject(url + "/groups/" + groupId, GroupNameServiceResponse.class);
        return response.getName();
    }
}
