package ru.practicum.publicApi.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.adminApi.event.EventShortDto;

import java.util.List;

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
