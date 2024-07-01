package ru.practicum.api.requestDto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class UpdateCompilationRequest {

    private List<Long> events;
    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

}
