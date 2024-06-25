package ru.practicum.api.requestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class NewCompilationDto {

    private List<Long> events;
    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

    public NewCompilationDto(List<Long> events, Boolean pinned, String title) {
        this.events = events;
        this.pinned = false; // default value
        this.title = title;
    }

}
