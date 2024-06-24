package ru.practicum.privateApi.event;

import ru.practicum.adminApi.location.Location;

public class NewEventDto {

    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String title;

}
