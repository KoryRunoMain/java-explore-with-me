package ru.practicum.api.publicApi.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.CommentDto;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.mapper.CommentMapper;
import ru.practicum.persistence.model.Comment;
import ru.practicum.persistence.repository.CommentRepository;
import ru.practicum.persistence.repository.EventRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getAllEventComments(Long eventId, int from, int size) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));

        List<Comment> comments = commentRepository.findAllByEventId(eventId, PageRequest.of(from / size, size));
        List<CommentDto> commentDtoList = commentMapper.getCommentsDtoList(comments);
        return commentDtoList == null ? Collections.emptyList() : commentDtoList;
    }

}
