package ru.practicum.api.adminApi.comment;

import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminCommentService {

    CommentDto getEventCommentByAdmin(Long comId);

    CommentDto updateCommentStatusByAdmin(Long comId, String status);

    CommentDto updateEventCommentByAdmin(Long userId, Long comId, NewCommentDto newCommentDto);

    List<CommentDto> searchCommentsByAdmin(String text, List<Long> events,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           String status, int from, int size);

}
