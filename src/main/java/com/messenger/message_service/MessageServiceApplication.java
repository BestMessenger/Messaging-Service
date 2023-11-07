package com.messenger.message_service;

import com.messenger.message_service.models.MessageModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}
}
