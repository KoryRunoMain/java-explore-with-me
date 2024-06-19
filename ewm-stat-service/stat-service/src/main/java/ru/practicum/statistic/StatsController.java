package ru.practicum.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatDto;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEndpointHit(@RequestBody EndpointHitDto hitDto) {
        log.info("Post-request, addEndpointHit={}", hitDto);
        service.addHit(hitDto);
    }

    @GetMapping("/stat")
    @ResponseStatus(HttpStatus.OK)
    public Page<ViewStatDto> getStats(@RequestParam String start, @RequestParam String end,
                                      @RequestParam(required = false) List<String> uris,
                                      @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Get-request, getStats=params: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return service.getStats(start, end, uris, unique);
    }

}