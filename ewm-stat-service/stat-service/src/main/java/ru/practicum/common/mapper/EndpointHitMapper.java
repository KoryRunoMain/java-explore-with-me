package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.EndpointHitDto;
import ru.practicum.persistence.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EndpointHitMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHit toEndpointHit(EndpointHitDto hitDto) {
        return EndpointHit.builder()
                .id(hitDto.getId())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), formatter))
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
