package ru.practicum.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.api.responseDto.LocationDto;
import ru.practicum.common.enums.PrivateStateAction;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(toBuilder = true)
public class NewEventDto {

    @NotNull
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @PositiveOrZero
    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    private String eventDate;

    @NotNull
    private LocationDto location;

    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private PrivateStateAction stateAction;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    public NewEventDto(String annotation, Long category, String description, String eventDate, LocationDto location, PrivateStateAction stateAction, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = false;
        this.participantLimit = 0;
        this.requestModeration = true;
        this.stateAction = PrivateStateAction.CANCEL_REVIEW;
        this.title = title;
    }

}
