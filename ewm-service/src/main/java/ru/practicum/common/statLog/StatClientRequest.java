package ru.practicum.common.statLog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StatClientRequest {
    private final RestTemplate restTemplate;
    private final String statServerUrl;

    public StatClientRequest(RestTemplate restTemplate, @Value("${stats-server.url}") String statServerUrl) {
        this.restTemplate = restTemplate;
        this.statServerUrl = statServerUrl;
    }

    public void addHit(HitDto hitDto) {
        try {
            restTemplate.postForObject(statServerUrl + "/hit", hitDto, Void.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
