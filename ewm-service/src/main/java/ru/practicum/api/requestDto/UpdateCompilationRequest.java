package ru.practicum.api.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateCompilationRequest {

    private List<Long> events;
    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

}
