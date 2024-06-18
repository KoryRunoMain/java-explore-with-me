package ru.practicum.statistic;

import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    /*
        TODO StatServiceImpl
     */

    @Override
    public void addHit(EndpointHitDto hitDto) {
    }

    @Override
    public List<ViewStatDto> getStats(String start, String end, List<String> uris, boolean unique) {
        return null;
    }

}
