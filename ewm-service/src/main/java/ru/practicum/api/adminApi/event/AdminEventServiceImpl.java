package ru.practicum.api.adminApi.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.requestDto.UpdateEventAdminRequest;
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
import java.util.List;
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
                rangeStart, rangeEnd, PageRequest.of(from/size, size));
        return events.stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest eventAdminRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("ADMIN-ERROR-response: eventId NotFound"));
        if (eventAdminRequest.getStateAction() != null) {
            switch (eventAdminRequest.getStateAction()) {

                case PUBLISH_EVENT:
                validatePublishEvent(event);
                event.setPublishedOn(LocalDateTime.now());
                event.setState(EventState.PUBLISHED);
                break;

                case REJECT_EVENT:
                    if (event.getState() == EventState.PUBLISHED) {
                        throw new ForbiddenException("ADMIN-ERROR-response: event is already published");
                    }
                    event.setState(EventState.CANCELED);
                    break;

                default:
                    throw new InvalidStateException("ADMIN-ERROR-response: Unknown state action: " +
                            eventAdminRequest.getStateAction());
            }
        }

        if (eventAdminRequest.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(eventAdminRequest.getEventDate(), formatter);
            if (eventDate.isBefore(LocalDateTime.now().plusDays(2))) {
                throw new ValidationException("ADMIN-ERROR-response: event cannot start earlier than 2 hours from now");
            }
            event.setEventDate(eventDate);
        }
        updateEventFields(event, eventAdminRequest);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    private void validateEventState(List<String> states) {
        if (states != null) {
            states.forEach(state -> {
                if (!isValidEventState(state)) {
                    throw new ValidationException("ADMIN-ERROR-response: undefined state value");
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
            throw new ForbiddenException("ADMIN-ERROR-response: undefined state value: " + event.getState());
        }
        if (event.getPublishedOn() != null && event.getEventDate().isBefore(event.getPublishedOn().minusHours(1))) {
            throw new ValidationException("Cannot publish the event because it's after 1 hour before event datetime");
        }
    }

    private void updateEventFields(Event event, UpdateEventAdminRequest eventDto) {
        if (eventDto.getTitle() != null)  {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(eventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("ADMIN-ERROR-response: category NotFound")));
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getLocation() != null) {
            Location location = event.getLocation();
            location.setLat(eventDto.getLocation().getLat());
            location.setLon(eventDto.getLocation().getLon());
            event.setLocation(location);
            locationRepository.save(location);
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(true);
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
    }

}
