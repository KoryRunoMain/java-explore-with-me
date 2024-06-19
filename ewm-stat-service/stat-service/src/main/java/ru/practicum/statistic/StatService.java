package ru.practicum.statistic;

import org.springframework.data.domain.Page;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import java.util.List;

public interface StatService {

    void addHit(EndpointHitDto hitDto);

    Page<ViewStatDto> getStats(String start, String end, List<String> uris, boolean unique);

}
