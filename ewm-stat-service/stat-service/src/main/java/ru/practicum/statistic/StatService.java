package ru.practicum.statistic;

import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    void addHit(EndpointHitDto hitDto);

    List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

}
