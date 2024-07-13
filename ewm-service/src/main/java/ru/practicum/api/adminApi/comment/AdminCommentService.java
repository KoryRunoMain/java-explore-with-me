package ru.practicum.api.adminApi.comment;

import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;

public interface AdminCommentService {

    CommentDto getEventCommentByAdmin(Long comId);

    CommentDto updateCommentStatusByAdmin(Long comId, String status);

    CommentDto updateEventCommentByAdmin(Long userId, Long comId, NewCommentDto newCommentDto);

}
