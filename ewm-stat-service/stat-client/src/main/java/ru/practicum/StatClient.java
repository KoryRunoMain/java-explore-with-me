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
import java.util.Optional;

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
        String url = (uris == null) ? withoutUrisUrl : withUrisUrl;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);
        Optional.ofNullable(uris).ifPresent(u -> parameters.put("uris", u));
        parameters.put("unique", unique);

        ResponseEntity<Object> clientResponse = rest.getForEntity(url, Object.class, parameters);
        return clientResponse.getStatusCode().is2xxSuccessful() ? clientResponse :
                ResponseEntity.status(clientResponse.getStatusCode())
                        .body(clientResponse.hasBody() ? clientResponse.getBody() : null);
    }

    public void addEndpointHit(EndpointHitDto hitDto) {
        rest.exchange(addUrl, HttpMethod.POST, new HttpEntity<>(hitDto), Object.class);
    }

}