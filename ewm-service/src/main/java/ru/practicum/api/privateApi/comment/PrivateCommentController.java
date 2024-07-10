package ru.practicum.api.privateApi.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PrivateCommentController {
    private final PrivateCommentService service;

    @PostMapping(path = "/users/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createEventCommentByUser(@Validated @Positive @PathVariable Long userId,
                                               @Validated @Positive @PathVariable Long eventId,
                                               @Validated @RequestBody NewCommentDto newCommentDto) {
        log.info("Post-request: createCommentByUser userId={} , eventId={} , newCommentDto={}",
                userId, eventId, newCommentDto);
        return service.createEventCommentByUser(userId, eventId, newCommentDto);
    }

    @PatchMapping(path = "/users/{userId}/comments/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateEventCommentByUser(@Validated @Positive @PathVariable Long userId,
                                               @Validated @Positive @PathVariable Long comId,
                                               @Validated @RequestBody NewCommentDto newCommentDto) {
        log.info("Patch-request: updateCommentByUser userId={}, comId={}, newCommentDto={}",
                userId, comId, newCommentDto);
        return service.updateEventCommentByUser(userId, comId, newCommentDto);
    }

    @DeleteMapping(path = "/users/{userId}/comments/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventCommentByUser(@Validated @Positive @PathVariable Long userId,
                                         @Validated @Positive @PathVariable Long comId) {
        log.info("Delete-request: deleteCommentByUser userId={}, comId={}", userId, comId);
        service.deleteEventCommentByUser(userId, comId);
    }

}
