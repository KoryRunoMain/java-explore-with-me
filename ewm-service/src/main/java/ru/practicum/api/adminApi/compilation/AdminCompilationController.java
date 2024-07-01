package ru.practicum.api.adminApi.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.responseDto.CompilationDto;
import ru.practicum.api.requestDto.NewCompilationDto;
import ru.practicum.api.requestDto.UpdateCompilationRequest;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AdminCompilationController {
    private final AdminCompilationService service;

    @PostMapping(path = "/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Validated @RequestBody NewCompilationDto compilationDto) {
        log.info("Post-request: createCompilation, compilationDto={}", compilationDto);
        return service.createCompilationByAdmin(compilationDto);
    }

    @DeleteMapping(path = "/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@Validated @Positive @PathVariable Long compId) {
        log.info("Delete-request: deleteCompilation, compId={}", compId);
        service.deleteCompilationByAdmin(compId);
    }

    @PatchMapping(path = "/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@Validated @Positive @PathVariable Long compId,
                                            @Validated @RequestBody UpdateCompilationRequest compilationRequest) {
        log.info("Patch-request: updateCompilation, compId={}, compilationDto={}", compId, compilationRequest);
        return service.updateCompilationByAdmin(compId, compilationRequest);
    }

}
