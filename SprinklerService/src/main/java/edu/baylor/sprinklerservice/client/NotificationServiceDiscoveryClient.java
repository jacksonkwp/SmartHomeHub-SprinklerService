package edu.baylor.sprinklerservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class NotificationServiceDiscoveryClient {
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public NotificationServiceDiscoveryClient(
            DiscoveryClient discoveryClient,
            RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    public String sendNotification(String authToken, String notification) {
        String response = null;
        List<ServiceInstance> instanceList = discoveryClient.getInstances("gateway-server");
        if (instanceList.isEmpty()) return null;

        String createNotificationStreamUri = String.format("%s/notification/v1/stream?name=sprinkler-notification-stream",
                instanceList.getFirst().getUri().toString());

        log.info("createNotificationStreamUri: {}", createNotificationStreamUri);

//        ResponseEntity<String> createStreamResponse = null;
//        try {
//            createStreamResponse = restClient.post()
//                    .uri(createNotificationStreamUri)
//                    .contentType(MediaType.TEXT_PLAIN)
//                    .body("")
//                    .retrieve()
//                    .toEntity(String.class);
//        } catch (Exception e) {
//            log.error("Failed to send notification", e);
//        }
//
//        log.info("createStreamResponse: status: {}, message: {}",
//                createStreamResponse.getStatusCode(),
//                createStreamResponse.getBody());
//        // TODO createStreamResponse is a string which needs to be parsed to get id. For now using 1

        String sendNotificationUri = String.format("%s/notification/v1/stream/1",
                instanceList.getFirst().getUri().toString());

        log.info("sendNotificationUri: {}", sendNotificationUri);

        ResponseEntity<String> sendNotificationResponse = null;
        try {
            sendNotificationResponse = restClient.post()
                    .uri(sendNotificationUri)
                    .contentType(MediaType.TEXT_PLAIN)
                    .header("Authorization", authToken)
                    .body(notification)
                    .retrieve().toEntity(String.class);
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }

        log.info("sendNotificationResponse: status: {}, message: {}",
                sendNotificationResponse.getStatusCode(),
                sendNotificationResponse.getBody());

        if (sendNotificationResponse.getStatusCode().is2xxSuccessful()) {
            response = sendNotificationResponse.getBody();
        }

        return response;
    }
}
