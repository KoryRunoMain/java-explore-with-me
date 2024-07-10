package ru.practicum.api.publicApi.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.responseDto.CommentDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PublicCommentController {
    private final PublicCommentService service;

    @GetMapping(path = "/events/{eventId}/comments")
    public List<CommentDto> getAllEventComments(@Validated @Positive @PathVariable Long eventId,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Get-request: getAllComments, eventId={} , from={} , size={}", eventId, from, size);
        return service.getAllEventComments(eventId, from, size);
    }

}
