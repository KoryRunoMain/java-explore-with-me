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
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Validated @RequestBody NewCompilationDto compilationDto) {
        log.info("Post-request: createCompilation, compilationDto={}", compilationDto);
        return service.createCompilationByAdmin(compilationDto);
    }

    @DeleteMapping(path = "/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@Validated @Positive @PathVariable Long compId) {
        log.info("Delete-request: deleteCompilation, compId={}", compId);
        service.deleteCompilationByAdmin(compId);
    }

    @PatchMapping(path = "{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@Validated @RequestBody UpdateCompilationRequest compilationRequest,
                                            @Validated @Positive @PathVariable Long comId) {
        log.info("Patch-request: updateCompilation, compId={}, compilationDto={}", comId, compilationRequest);
        return service.updateCompilationByAdmin(comId, compilationRequest);
    }

}
