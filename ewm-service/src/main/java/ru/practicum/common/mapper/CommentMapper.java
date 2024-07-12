package ru.practicum.common.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.api.requestDto.NewCommentDto;
import ru.practicum.api.responseDto.CommentDto;
import ru.practicum.common.enums.CommentStatus;
import ru.practicum.persistence.model.Comment;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    public Comment toNewComment(User user, Event event, NewCommentDto newCommentDto) {
        return Comment.builder()
                .event(event)
                .author(user)
                .created(LocalDateTime.now())
                .status(CommentStatus.PENDING)
                .text(newCommentDto.getText())
                .build();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .event(eventMapper.toEventShortDto(comment.getEvent()))
                .created(comment.getCreated().format(formatter))
                .author(userMapper.toUserShortDto(comment.getAuthor()))
                .status(comment.getStatus().toString())
                .text(comment.getText())
                .build();
    }

    public List<CommentDto> getCommentsDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }

    public void adminUpdateCommentFromDto(Comment comment, NewCommentDto newCommentDto) {
        comment.setText(newCommentDto.getText());
        comment.setCreated(LocalDateTime.now());
        comment.setStatus(CommentStatus.APPROVED);
    }

    public void privateUpdateCommentFromDto(Comment comment, NewCommentDto newCommentDto) {
        comment.setText(newCommentDto.getText());
        comment.setCreated(LocalDateTime.now());
        comment.setStatus(CommentStatus.PENDING);
    }

}
