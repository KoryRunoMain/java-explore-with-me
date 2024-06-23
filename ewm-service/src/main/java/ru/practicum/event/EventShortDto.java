package ru.practicum.event;

import ru.practicum.category.CategoryDto;
import ru.practicum.user.UserShortDto;

public class EventShortDto {

    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private Long views;

}
