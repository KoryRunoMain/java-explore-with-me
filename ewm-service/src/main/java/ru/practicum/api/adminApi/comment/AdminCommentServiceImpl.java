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
import ru.practicum.persistence.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto updateCommentStatusByAdmin(Long comId, String requestStatus) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));

        if (requestStatus != null) {
            Map<CommentStatus, Consumer<Comment>> actions = createActionsMap();
            CommentStatus commentStatus = CommentStatus.valueOf(requestStatus);
            Consumer<Comment> action = actions.get(commentStatus);

            if (action != null) {
                action.accept(comment);
            } else {
                throw new InvalidStateException(String.format("Unknown status value=%s", requestStatus));
            }
        }

        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto updateEventCommentByAdmin(Long userId, Long comId, NewCommentDto newCommentDto) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found,", userId)));
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));

        if (comment.getAuthor() == null) {
            throw new NotFoundException("Comment does not have author");
        }
        comment.setText(newCommentDto.getText());
        comment.setCreated(LocalDateTime.now());
        comment.setStatus(CommentStatus.APPROVED);

        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto getEventCommentByAdmin(Long comId) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));
        return commentMapper.toCommentDto(comment);
    }

    private Map<CommentStatus, Consumer<Comment>> createActionsMap() {
        Map<CommentStatus, Consumer<Comment>> actions = new HashMap<>();
        actions.put(CommentStatus.PENDING, (comment) -> comment.setStatus(CommentStatus.PENDING));
        actions.put(CommentStatus.APPROVED, (comment) -> comment.setStatus(CommentStatus.APPROVED));
        actions.put(CommentStatus.REJECTED, (comment) -> comment.setStatus(CommentStatus.REJECTED));
        actions.put(CommentStatus.SPAM, (comment) -> comment.setStatus(CommentStatus.SPAM));
        actions.put(CommentStatus.BLOCKED, (comment) -> comment.setStatus(CommentStatus.BLOCKED));
        return actions;
    }

}
