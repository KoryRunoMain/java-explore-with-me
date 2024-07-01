package ru.practicum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import ru.practicum.common.exception.ValidationRequestException;
import ru.practicum.common.mapper.EndpointHitMapper;
import ru.practicum.persistence.EndpointHit;
import ru.practicum.common.mapper.ViewStatMapper;
import ru.practicum.persistence.StatRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
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
    public EndpointHitDto addHit(EndpointHitDto hitDto) {
        EndpointHit hit = repository.save(hitMapper.toEndpointHit(hitDto));
        return hitMapper.toEndpointHitDto(hit);
    }

    @Override
    public List<ViewStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new ValidationRequestException("Bad params, start is after end!");
        }
        if (unique) {
            return getAllUniqueViewStatList(start, end, uris, Pageable.ofSize(10));
        }
        return getAllViewStatList(start, end, uris, Pageable.ofSize(10));
    }

    private List<ViewStatDto> getAllViewStatList(LocalDateTime start, LocalDateTime end, List<String> uris, Pageable page) {
        return getViewStatDtoList(repository.findAllViewStatList(start, end, uris, page));
    }

    private List<ViewStatDto> getAllUniqueViewStatList(LocalDateTime start, LocalDateTime end, List<String> uris, Pageable page) {
        return getViewStatDtoList(repository.findAllUniqueViewStatList(start, end, uris, page));
    }

    private List<ViewStatDto> getViewStatDtoList(List<ViewStat> viewStats) {
        List<ViewStatDto> viewStat = viewStats.stream().map(statMapper::toViewStatDto).collect(Collectors.toList());
        return viewStat.isEmpty() ? Collections.emptyList() : viewStat;
    }

}