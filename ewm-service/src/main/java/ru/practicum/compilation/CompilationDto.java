package ru.practicum.compilation;

import ru.practicum.event.EventShortDto;

import java.util.List;

public class CompilationDto {

    private Long id;
    private boolean pinned;
    private String title;
    public List<EventShortDto> events;

}
