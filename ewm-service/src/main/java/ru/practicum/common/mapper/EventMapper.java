package ru.practicum.common.mapper;

import ru.practicum.api.requestDto.NewEventDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.persistence.model.Event;
import ru.practicum.api.responseDto.EventFullDto;
import ru.practicum.api.responseDto.EventShortDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final LocationMapper locationMapper;
    private final ParticipationMapper participationMapper;

    public EventFullDto toEeventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(String.valueOf(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(String.valueOf(event.getEventDate()))
                .id(event.getId())
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(String.valueOf(event.getPublishedOn()))
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .eventDate(String.valueOf(event.getEventDate()))
                .confirmedRequests(participationMapper.confirmedRequestsCounts((event.getRequests())))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .views(event.getViews())
                .build();
    }

    public Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .paid(newEventDto.isPaid())
                .title(newEventDto.getTitle())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate()))
                .location(locationMapper.toLocation(newEventDto.getLocation()))
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(String.valueOf(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(String.valueOf(event.getEventDate()))
                .id(event.getId())
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(String.valueOf(event.getPublishedOn()))
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public void updateEventDetails(Event event, NewEventDto newEventDto) {
        Optional.ofNullable(newEventDto.getTitle()).ifPresent(event::setTitle);
        Optional.ofNullable(newEventDto.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(newEventDto.getDescription()).ifPresent(event::setDescription);
        Optional.of(newEventDto.isPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(newEventDto.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.of(newEventDto.isRequestModeration()).ifPresent(event::setRequestModeration);
    }

    public void updateEventDate(Event event, NewEventDto newEventDto) {
        if (newEventDto.getEventDate() != null) {
            LocalDateTime newEventDate = LocalDateTime.parse(newEventDto.getEventDate(), formatter);
            event.setEventDate(newEventDate);
        }
    }

}
