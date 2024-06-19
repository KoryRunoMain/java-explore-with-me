package ru.practicum.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import ru.practicum.statistic.model.EndpointHitMapper;
import ru.practicum.statistic.model.ViewStat;
import ru.practicum.statistic.model.ViewStatMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional()
public class StatServiceImpl implements StatService {
    private final StatRepository repository;
    private final EndpointHitMapper hitMapper;
    private final ViewStatMapper statMapper;

    @Transactional
    @Override
    public void addHit(EndpointHitDto hitDto) {
        repository.save(hitMapper.toEndpointHit(hitDto));
    }

    @Override
    public Page<ViewStatDto> getStats(String startTime, String endTime, List<String> uris, boolean unique) {
        Pageable page = Pageable.ofSize(10);
        LocalDateTime start = toLocalDateTimeFormat(startTime);
        LocalDateTime end = toLocalDateTimeFormat(endTime);
        return unique ? getAllUniqueViewStatList(start, end, uris, page) : getAllViewStatList(start, start, uris, page);
    }

    private Page<ViewStatDto> getAllViewStatList(LocalDateTime start, LocalDateTime end,
                                                 List<String> uris, Pageable page) {
        Page<ViewStat> viewStats = repository.findAllViewStatList(start, end, uris, page);
        return getViewStatDtoList(viewStats);
    }

    private Page<ViewStatDto> getAllUniqueViewStatList(LocalDateTime start, LocalDateTime end,
                                                       List<String> uris, Pageable page) {
        Page<ViewStat> viewStats = repository.findAllUniqueViewStatList(start, end, uris, page);
        return getViewStatDtoList(viewStats);
    }

    private LocalDateTime toLocalDateTimeFormat(String pattern) {
        return LocalDateTime.parse(pattern, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private Page<ViewStatDto> getViewStatDtoList(Page<ViewStat> viewStats) {
        return viewStats.map(statMapper::toViewStatDto);
    }

}
