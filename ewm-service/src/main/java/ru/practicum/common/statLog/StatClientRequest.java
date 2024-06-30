package ru.practicum.common.statLog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class StatClientRequest {
    private static final String STAT_SERVER_URL = "${stats-server.url}" + "/hit";
    private final RestTemplate restTemplate;

    public void addHit(HitDto hitDto) {
        restTemplate.postForObject(STAT_SERVER_URL, hitDto, Void.class);
    }

}
