package ru.practicum.statistic;

import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import java.util.List;

public interface StatService {

    void addHit(EndpointHitDto hitDto);

    List<ViewStatDto> getStats(String start, String end, List<String> uris, boolean unique);

}
