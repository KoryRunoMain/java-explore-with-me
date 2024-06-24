package ru.practicum.adminApi.compilation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NewCompilationDto {

    private List<Long> events;
    private boolean pinned;
    private String title;

}
