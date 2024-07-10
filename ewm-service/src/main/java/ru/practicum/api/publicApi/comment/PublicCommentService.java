package ru.practicum.api.publicApi.comment;

import ru.practicum.api.responseDto.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> getAllEventComments(Long eventId, int from, int size);

}
