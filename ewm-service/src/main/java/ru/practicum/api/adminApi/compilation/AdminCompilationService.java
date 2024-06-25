package ru.practicum.api.adminApi.compilation;

import ru.practicum.api.responseDto.CompilationDto;
import ru.practicum.api.requestDto.NewCompilationDto;
import ru.practicum.api.requestDto.UpdateCompilationRequest;

public interface AdminCompilationService {

    CompilationDto createCompilationByAdmin(NewCompilationDto compilationDto);

    void deleteCompilationByAdmin(Long compId);

    CompilationDto updateCompilationByAdmin(Long compId, UpdateCompilationRequest compilationRequest);

}
