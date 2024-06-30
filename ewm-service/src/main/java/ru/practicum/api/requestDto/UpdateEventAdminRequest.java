package ru.practicum.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.common.enums.AdminStateAction;
import ru.practicum.persistence.model.Location;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UpdateEventAdminRequest {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private AdminStateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;

}
