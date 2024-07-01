package ru.practicum.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class NewCompilationDto {

    private List<Long> events;
    private Boolean pinned = false;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

}
