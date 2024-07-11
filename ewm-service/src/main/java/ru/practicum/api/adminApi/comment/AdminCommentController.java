package ru.practicum.api.adminApi.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AdminCommentController {
    private final AdminCommentService service;

    @GetMapping(path = "/admin/comments/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getEventCommentByAdmin(@Validated @Positive @PathVariable Long comId) {
        log.info("Get-request: getEventCommentByAdmin comId={}", comId);
        return service.getEventCommentByAdmin(comId);
    }

    @PatchMapping(path = "/admin/{userId}/comments/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateEventCommentByAdmin(@Validated @Positive @PathVariable Long userId,
                                                @Validated @Positive @PathVariable Long comId,
                                                @Validated @RequestBody NewCommentDto newCommentDto) {
        log.info("Patch-request: updateCommentByUser userId={}, comId={}, newCommentDto={}",
                userId, comId, newCommentDto);
        return service.updateEventCommentByAdmin(userId, comId, newCommentDto);
    }

    @PatchMapping(path = "/admin/comments/{comId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentStatusByAdmin(@Validated @Positive @PathVariable Long comId,
                                                 @RequestParam(defaultValue = "PENDING") String status) {
        log.info("Patch-request: updateEventCommentStatusByAdmin comId={}, userId={}", comId, status);
        return service.updateCommentStatusByAdmin(comId, status);
    }

}
