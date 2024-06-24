package ru.practicum.adminApi.compilation;

import lombok.*;
import ru.practicum.adminApi.event.EventShortDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CompilationDto {

    private Long id;
    private boolean pinned;
    private String title;
    public List<EventShortDto> events;

}
