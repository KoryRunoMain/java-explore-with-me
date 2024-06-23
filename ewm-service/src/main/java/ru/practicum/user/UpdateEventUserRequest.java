package ru.practicum.user;

import ru.practicum.entity.Location;

import javax.validation.constraints.Size;

public class UpdateEventUserRequest {

    protected enum StateAction {
        SEND_TO_REVIEW,
        CANCEL_REVIEW
    }

    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;

}
