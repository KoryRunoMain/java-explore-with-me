package ru.practicum.api.adminApi.comment;

import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminCommentService {

    CommentDto updateEventCommentStatusByAdmin(Long comId, String status);

    CommentDto getEventCommentByAdmin(Long comId);

    List<CommentDto> searchCommentsByAdmin(String text, List<Long> events, LocalDateTime rangeStart, LocalDateTime rangeEnd, String status, int from, int size);
}
