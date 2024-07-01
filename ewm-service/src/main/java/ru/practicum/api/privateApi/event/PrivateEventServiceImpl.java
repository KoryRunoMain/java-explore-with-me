package ru.practicum.api.privateApi.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.api.requestDto.NewEventDto;
import ru.practicum.api.requestDto.UpdateEventUserRequest;
import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.responseDto.EventShortDto;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.exception.ForbiddenException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.common.mapper.LocationMapper;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.model.Location;
import ru.practicum.persistence.model.User;
import ru.practicum.persistence.repository.CategoryRepository;
import ru.practicum.persistence.repository.EventRepository;
import ru.practicum.persistence.repository.LocationRepository;
import ru.practicum.persistence.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;

    @Override
    public List<EventShortDto> getUserEventsByUser(Long userId, int from, int size) {
        validateUser(userId);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, PageRequest.of(from/size, size));
        return events.stream().map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto createEventByUser(Long userId, NewEventDto newEventDto) {
        validateDate(newEventDto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: userId NotFound"));
        Event event = eventMapper.toEvent(newEventDto);
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(user);
        event.setState(EventState.PENDING);
        event.setLocation(locationRepository.save(locationMapper.toLocation(newEventDto.getLocation())));
        event.setCategory(categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: category NotFound")));
        event.setViews(0L);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        validateUser(userId);
        return eventMapper.toEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: eventID NotFound")));
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest newEventDto) {
        validateUser(userId);

        Event event = getEventById(eventId);
        validateEventState(event);
        validateDate(newEventDto);

        eventMapper.updateEventDetails(event, newEventDto);
        eventMapper.updateEventDate(event, newEventDto);
        updateEventCategory(event, newEventDto);
        updateEventLocation(event, newEventDto);
        updateEventState(event, newEventDto);

        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: eventID NotFound"));
    }

    private void updateEventCategory(Event event, UpdateEventUserRequest newEventDto) {
        if (newEventDto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(newEventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: category NotFound")));
        }
    }

    private void updateEventState(Event event, UpdateEventUserRequest newEventDto) {
        if (newEventDto.getStateAction() != null) {
            switch (newEventDto.getStateAction()) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
            }
        }
    }

    private void updateEventLocation(Event event, UpdateEventUserRequest newEventDto) {
        if (newEventDto.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(newEventDto.getLocation().getLat());
            location.setLon(newEventDto.getLocation().getLon());
            event.setLocation(locationRepository.save(location));
        }
    }

    private void validateUser(Long userId) {
        eventRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("PRIVATE-MESSAGE-response: userID NotFound"));
    }

    private void validateDate(NewEventDto newEvent) {
        LocalDateTime dateTime = LocalDateTime.parse(newEvent.getEventDate(), formatter);
        if (dateTime.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("PRIVATE-MESSAGE-response: event cannot start earlier than 2 hours from now");
        }
    }

    private void validateDate(UpdateEventUserRequest newEvent) {
        LocalDateTime dateTime = LocalDateTime.parse(newEvent.getEventDate(), formatter);
        if (dateTime.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("PRIVATE-MESSAGE-response: event cannot start earlier than 2 hours from now");
        }
    }

    private void validateEventState(Event event) {
        if (!EnumSet.of(EventState.PENDING, EventState.CANCELED).contains(event.getState())) {
            throw new ForbiddenException("PRIVATE-MESSAGE-response: Only pending or canceled events can be changed");
        }
    }

}
