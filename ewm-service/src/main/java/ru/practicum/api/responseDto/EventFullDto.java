package ru.practicum.api.responseDto;

import lombok.*;
import ru.practicum.common.enums.EventState;
import ru.practicum.persistence.model.Location;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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