package ru.practicum.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateCompilationRequest {

    private List<Long> events;
    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

}
