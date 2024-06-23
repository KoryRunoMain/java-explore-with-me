package ru.practicum.event;

import ru.practicum.category.CategoryDto;
import ru.practicum.entity.Location;
import ru.practicum.user.UserShortDto;

public class EventFullDto {

    protected enum EventState {
        PENDING,
        PUBLISHED,
        CANCELED
    }

    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private EventState state;
    private String title;
    private Long views;

}
