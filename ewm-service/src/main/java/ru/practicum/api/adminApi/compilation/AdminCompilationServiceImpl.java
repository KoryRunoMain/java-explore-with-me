package ru.practicum.api.adminApi.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.CompilationDto;
import ru.practicum.api.requestDto.NewCompilationDto;
import ru.practicum.api.requestDto.UpdateCompilationRequest;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.exception.ValidationException;
import ru.practicum.common.mapper.CompilationMapper;
import ru.practicum.persistence.model.Compilation;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.repository.CompilationRepository;
import ru.practicum.persistence.repository.EventRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto createCompilationByAdmin(NewCompilationDto compilationDto) {
        Compilation compilation = compilationMapper.toCompilation(compilationDto);
        setCompilationEvents(compilation, compilationDto.getEvents());
        return compilationMapper.toCompilationDto(
                compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilationByAdmin(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("ADMIN-ERROR-response: compilation NotFound"));
        compilationRepository.delete(compilation);
    }

    @Override
    public CompilationDto updateCompilationByAdmin(Long compId, UpdateCompilationRequest compilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("ADMIN-ERROR-response: compilation NotFound"));
        if (compilationRequest.getTitle() != null) {
            String title = compilationRequest.getTitle();
            if (title.isEmpty()) {
                throw new ValidationException("ADMIN-ERROR-response: title isEmpty");
            }
            compilation.setTitle(title);
        }
        if (compilationRequest.getPinned() != null) {
            compilation.setPinned(compilationRequest.getPinned());
        }
        setCompilationEvents(compilation, compilationRequest.getEvents());
        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    private void setCompilationEvents(Compilation compilation, List<Long> eventIds) {
        List<Event> events = eventRepository.findByIdIn(eventIds);
        compilation.setEvents(events);
    }

}
