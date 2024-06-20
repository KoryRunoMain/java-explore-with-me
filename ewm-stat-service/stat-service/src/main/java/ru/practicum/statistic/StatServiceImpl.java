package ru.practicum.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import ru.practicum.statistic.exception.BadRequestException;
import ru.practicum.statistic.model.EndpointHitMapper;
import ru.practicum.statistic.model.ViewStat;
import ru.practicum.statistic.model.ViewStatMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
    public List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new BadRequestException("Error! Bad params, start is after end!");
        }
        Pageable page = Pageable.ofSize(10);
        return unique ? getAllUniqueViewStatList(start, end, uris, page) : getAllViewStatList(start, start, uris, page);
    }

    private List<ViewStatDto> getAllViewStatList(LocalDateTime start, LocalDateTime end,
                                                 List<String> uris, Pageable page) {
        List<ViewStat> viewStats = repository.findAllViewStatList(start, end, uris, page);
        return getViewStatDtoList(viewStats);
    }

    private List<ViewStatDto> getAllUniqueViewStatList(LocalDateTime start, LocalDateTime end,
                                                       List<String> uris, Pageable page) {
        List<ViewStat> viewStats = repository.findAllUniqueViewStatList(start, end, uris, page);
        return getViewStatDtoList(viewStats);
    }

    private List<ViewStatDto> getViewStatDtoList(List<ViewStat> viewStats) {
        return viewStats.stream().map(statMapper::toViewStatDto).collect(Collectors.toList());
    }

}
