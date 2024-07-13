package ru.practicum.api.adminApi.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.requestDto.UpdateEventAdminRequest;
import ru.practicum.common.enums.AdminStateAction;
import ru.practicum.common.enums.EventState;
import ru.practicum.common.exception.ForbiddenException;
import ru.practicum.common.exception.InvalidStateException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.common.mapper.EventMapper;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.model.Location;
import ru.practicum.persistence.repository.CategoryRepository;
import ru.practicum.persistence.repository.EventRepository;
import ru.practicum.persistence.repository.LocationRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEventServiceImpl implements AdminEventService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventFullDto> getAllEventsByAdmin(List<Long> users, List<String> states,
                                                  List<Long> categories, LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd, int from, int size) {
        validateEventState(states);
        List<Event> events = eventRepository.findAllEventsByAdmin(users, states, categories,
                rangeStart, rangeEnd, PageRequest.of(from / size, size));

        return events.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest eventAdminRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));


        AdminStateAction state = eventAdminRequest.getStateAction();
        if (state != null) {
            Map<AdminStateAction, Consumer<Event>> eventActions = createEventActionsMap();
            Consumer<Event> action = eventActions.get(eventAdminRequest.getStateAction());

            if (action != null) {
                action.accept(event);
            } else {
                throw new InvalidStateException(String.format("Unknown status value=%s", state));
            }
        }

        if (eventAdminRequest.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(eventAdminRequest.getEventDate(), formatter);
            if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ValidationException("Event cannot start earlier than 2 hours from now");
            }
            event.setEventDate(eventDate);
        }

        updateEventFields(event, eventAdminRequest);
        eventRepository.save(event);
        return eventMapper.toEventFullDto(event);
    }

    private void validateEventState(List<String> states) {
        if (states != null) {
            states.forEach(state -> {
                if (!isValidEventState(state)) {
                    throw new ValidationException("Unknown state: " + state);
                }
            });
        }
    }

    private boolean isValidEventState(String state) {
        try {
            EventState.valueOf(state);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void validatePublishEvent(Event event) {
        if (event.getState() != EventState.PENDING) {
            throw new ForbiddenException("Cannot publish the event because it's not in the right state: " + event.getState());
        }
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
            throw new ValidationException("Cannot publish the event earlier than 1 hour");
        }
    }

    private void updateEventFields(Event event, UpdateEventAdminRequest eventDto) {
        Optional.ofNullable(eventDto.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(eventDto.getTitle()).ifPresent(event::setTitle);
        Optional.ofNullable(eventDto.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(eventDto.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(eventDto.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(eventDto.getRequestModeration()).ifPresent(event::setRequestModeration);

        if (eventDto.getCategory() != null) {
            Long catId = eventDto.getCategory();
            event.setCategory(categoryRepository.findById(catId)
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId))));
        }

        if (eventDto.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(eventDto.getLocation().getLat());
            location.setLon(eventDto.getLocation().getLon());
            event.setLocation(location);
            locationRepository.save(location);
        }
    }

    private Map<AdminStateAction, Consumer<Event>> createEventActionsMap() {
        Map<AdminStateAction, Consumer<Event>> actions = new HashMap<>();

        actions.put(AdminStateAction.PUBLISH_EVENT, (event) -> {
            validatePublishEvent(event);
            event.setState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        });

        actions.put(AdminStateAction.REJECT_EVENT, (event) -> {
            if (event.getState() == EventState.PUBLISHED) {
                throw new ForbiddenException("Cannot reject, the event is already published");
            }
            event.setState(EventState.CANCELED);
        });

        return actions;
    }

}
