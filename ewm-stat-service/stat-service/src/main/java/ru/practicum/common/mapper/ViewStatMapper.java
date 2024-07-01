package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ViewStatDto;
import ru.practicum.api.ViewStat;

@Component
public class ViewStatMapper {

    public ViewStatDto toViewStatDto(ViewStat stat) {
        return ViewStatDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(stat.getHits())
                .build();
    }

}
