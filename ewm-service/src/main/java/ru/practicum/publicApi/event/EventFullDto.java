package ru.practicum.publicApi.event;

import ru.practicum.adminApi.account.UserShortDto;
import ru.practicum.adminApi.category.CategoryDto;
import ru.practicum.adminApi.location.Location;
import ru.practicum.adminApi.event.EventState;

public class EventFullDto {

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
