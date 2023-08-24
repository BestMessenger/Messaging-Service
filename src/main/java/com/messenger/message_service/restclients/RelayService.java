package com.messenger.message_service.restclients;

import com.messenger.message_service.dto.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RelayService {

    @Value("${relay-service-url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public void saveMessageInDatabase(MessageEntity request) {
        HttpEntity<MessageEntity> messageDtoHttpEntity = new HttpEntity<>(request);
        restTemplate.postForObject(url + "/messages", request, String.class);
    }

    public List<MessageEntity> getMessagesByGroupId(int groupId) {
        return restTemplate.exchange(
                url + "/" + groupId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MessageEntity>>() {
                }
        ).getBody();
    }


}
