package ru.practicum.statistic.model;

import org.springframework.stereotype.Component;
import ru.practicum.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EndpointHitMapper {

    public EndpointHit toEndpointHit(EndpointHitDto hitDto) {
        return EndpointHit.builder()
                .id(hitDto.getId())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public EndpointHitDto toEndpointHitDto(EndpointHit hit) {
        return EndpointHitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().toString())
                .build();
    }

}
