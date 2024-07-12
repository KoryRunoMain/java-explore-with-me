package ru.practicum.api.adminApi.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;
import ru.practicum.common.enums.CommentStatus;
import ru.practicum.common.exception.InvalidStateException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.mapper.CommentMapper;
import ru.practicum.persistence.model.Comment;
import ru.practicum.persistence.repository.CommentRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto updateCommentStatusByAdmin(Long comId, String requestStatus) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));

        CommentStatus status = Optional.ofNullable(requestStatus)
                .flatMap(CommentStatus::from)
                .orElseThrow(() -> new InvalidStateException(String.format("Unknown status value=%s", requestStatus)));

        comment.setStatus(status);
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto updateEventCommentByAdmin(Long userId, Long comId, NewCommentDto newCommentDto) {
        Comment comment = commentRepository.findByIdAndAuthorId(comId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d and authorId=%d was not found,",
                        comId, userId)));
        commentMapper.adminUpdateCommentFromDto(comment, newCommentDto);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto getEventCommentByAdmin(Long comId) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));
        return commentMapper.toCommentDto(comment);
    }

}
