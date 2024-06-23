package ru.practicum.event;

import ru.practicum.entity.Location;

import javax.validation.constraints.Size;

public class UpdateEventAdminRequest {

    protected enum StateAction {
        PUBLISH_EVENT,
        REJECT_EVENT
    }

    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String stateAction;

    @Size(min = 3, max = 120)
    private String title;

}
