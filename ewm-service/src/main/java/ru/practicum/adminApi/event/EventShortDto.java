package ru.practicum.adminApi.event;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.adminApi.category.CategoryDto;
import ru.practicum.adminApi.account.UserShortDto;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
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
