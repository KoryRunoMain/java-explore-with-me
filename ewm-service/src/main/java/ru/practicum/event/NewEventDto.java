package ru.practicum.event;

import ru.practicum.entity.Location;

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
