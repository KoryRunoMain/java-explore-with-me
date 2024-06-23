package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatClient {
    private final RestTemplate rest;
    private final String withUrisUrl;
    private final String withoutUrisUrl;
    private final String addUrl;

    public StatClient(RestTemplate rest, @Value("${stats-server.url}") String serviceUrl) {
        this.rest = rest;
        this.withUrisUrl = serviceUrl + "/stats/?start={start}&end={end}&unique={unique}";
        this.withoutUrisUrl = serviceUrl + "/stats/?start={start}&end={end}&uris={uris}&unique={unique}";
        this.addUrl = serviceUrl + "/hit";
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, boolean unique) {
        String url = (uris == null || uris.isEmpty()) ? withoutUrisUrl : withUrisUrl;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("uris", uris);
        parameters.put("unique", unique);
        return rest.getForEntity(url, Object.class, parameters);
    }

    public void addEndpointHit(EndpointHitDto hitDto) {
        rest.exchange(addUrl, HttpMethod.POST, new HttpEntity<>(hitDto), Object.class);
    }

}