package ru.practicum.statistic;

import org.springframework.data.domain.Page;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    void addHit(EndpointHitDto hitDto);

    Page<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

}
