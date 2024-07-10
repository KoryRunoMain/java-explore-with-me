package ru.practicum.api.adminApi.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.CommentDto;
import ru.practicum.common.enums.CommentStatus;
import ru.practicum.common.exception.InterruptedTreadException;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.mapper.CommentMapper;
import ru.practicum.persistence.model.Comment;
import ru.practicum.persistence.repository.CommentRepository;
import ru.practicum.persistence.repository.EventRepository;
import ru.practicum.persistence.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto updateEventCommentStatusByAdmin(Long comId, String status) {
         Runnable runComment = () -> {
             Comment comment = commentRepository.findById(comId)
                     .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));
             if (status.equals(CommentStatus.APPROVED.toString())) {
                 comment.setStatus(CommentStatus.APPROVED);
             }
             if (status.equals(CommentStatus.REJECTED.toString())) {
                 comment.setStatus(CommentStatus.REJECTED);
             }
             if (status.equals(CommentStatus.SPAM.toString())) {
                 comment.setStatus(CommentStatus.SPAM);
             }
             if (status.equals(CommentStatus.PENDING.toString())) {
                 comment.setStatus(CommentStatus.PENDING);
             }
             comment.setCreated(LocalDateTime.now());
             commentRepository.save(comment);
         };

         Thread thread = new Thread(runComment);
         thread.start();

         try {
             thread.join();
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
             throw new InterruptedTreadException("Thread was interrupted");
         }

        Comment updatedComment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));
        return commentMapper.toCommentDto(updatedComment);
    }

    @Override
    public CommentDto getEventCommentByAdmin(Long comId) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d was not found,", comId)));
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> searchCommentsByAdmin(String text, List<Long> events,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  String status, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        List<Comment> commentList = commentRepository.searchComments(text, events, rangeStart,rangeEnd, status, page);

        return commentList.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

}
