package ru.practicum.statistic.model;

import org.springframework.stereotype.Component;
import ru.practicum.ViewStatDto;

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
