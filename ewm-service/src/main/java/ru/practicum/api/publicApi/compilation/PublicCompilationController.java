package ru.practicum.api.publicApi.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.responseDto.CompilationDto;
import ru.practicum.common.exception.NotFoundException;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationController {
    private final PublicCompilationService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @Validated @Positive @RequestParam(defaultValue = "0") int from,
                                                @Validated @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("PUBLIC-GET-request, getCompilations: pinned={}, from={}, size={}", pinned, from, size);
        return service.getAllCompilations(pinned, from, size);
    }

    @GetMapping(path = "/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilation(@Validated @Positive @PathVariable Long compId) {
        log.info("PUBLIC-GET-request, getCompilation: compId={}", compId);
        return service.getCompilationById(compId);
    }

}
