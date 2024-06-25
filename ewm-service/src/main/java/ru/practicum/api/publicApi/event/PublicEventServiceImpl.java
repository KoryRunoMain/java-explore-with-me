package ru.practicum.api.publicApi.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.EventShortDto;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.mapper.ParticipationMapper;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.repository.EventRepository;
import ru.practicum.api.responseDto.EventFullDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository repository;
    private final EventMapper eventMapper;
    private final ParticipationMapper participationMapper;

    @Override
    public List<EventShortDto> getAllPublicEvents(String text, List<Long> categories, boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  boolean onlyAvailable, String sort, int from, int size) {
        validateDateRange(rangeStart, rangeEnd);
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = repository.findAllPublicEvents(text, categories, paid, rangeStart, rangeEnd, pageable);

        if (onlyAvailable) {
            events = events.stream()
                   .filter(event -> participationMapper.confirmedRequestsCounts(event.getRequests()) < event.getParticipantLimit())
                    .collect(Collectors.toList());
        }

        List<EventShortDto> shortEvents = events.stream().map(eventMapper::toEventShortDto).collect(Collectors.toList());
        if (sort != null) {
            sortEvents(shortEvents, sort);
        }
        return shortEvents.isEmpty() ? Collections.emptyList() : shortEvents;
    }

    @Override
    public EventFullDto getPublicEvent(Long eventId) {
        Event event = repository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("PUBLIC-ERROR-response: event NotFound"));
        return eventMapper.toEeventFullDto(event);
    }

    private void validateDateRange(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart.isAfter(rangeEnd) && rangeEnd != null) {
            throw new ValidationException("PUBLIC-ERROR-response: rangeStart isAfter rangeEnd or Null");
        }
    }

    private void sortEvents(List<EventShortDto> events, String sort) {
        switch (sort) {
            case "EVENT_DATE":
                events.sort(Comparator.comparing(EventShortDto::getEventDate));
                break;
            case "VIEWS":
                events.sort(Comparator.comparing(EventShortDto::getViews));
                break;
            default:
                throw new ValidationException("PUBLIC-ERROR-response: undefined sort value");
        }
    }

}
