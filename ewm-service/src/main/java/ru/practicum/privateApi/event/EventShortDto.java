package ru.practicum.privateApi.event;

import ru.practicum.adminApi.account.UserShortDto;
import ru.practicum.adminApi.category.CategoryDto;

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
