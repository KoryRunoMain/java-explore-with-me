package ru.practicum.api.privateApi.comment;

import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;

public interface PrivateCommentService {

    CommentDto createEventCommentByUser(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto updateEventCommentByUser(Long userId, Long comId, NewCommentDto newCommentDto);

    void deleteEventCommentByUser(Long userId, Long comId);

}
