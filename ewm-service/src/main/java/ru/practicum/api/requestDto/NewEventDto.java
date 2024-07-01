package ru.practicum.api.requestDto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.practicum.api.responseDto.LocationDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@ToString
@Builder(toBuilder = true)
public class NewEventDto {

    @NotNull
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @PositiveOrZero
    private long category;

    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    private String eventDate;

    @NotNull
    private LocationDto location;

    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    public NewEventDto(String annotation, Long category, String description, String eventDate, LocationDto location,
                       boolean paid, int participantLimit, boolean requestModeration, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = false;
        this.participantLimit = 0;
        this.requestModeration = true;
        this.title = title;
    }

}
