package ru.practicum.common.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.api.responseDto.CompilationDto;
import ru.practicum.api.responseDto.EventShortDto;
import ru.practicum.persistence.model.Compilation;
import ru.practicum.api.requestDto.NewCompilationDto;
import ru.practicum.persistence.model.Event;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    public Compilation toCompilation(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(getEventShortDtoList(compilation.getEvents()))
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    private List<EventShortDto> getEventShortDtoList(List<Event> events) {
        return events != null ? events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList()) : Collections.emptyList();

    }
}
