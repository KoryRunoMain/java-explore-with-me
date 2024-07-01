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
        if (compilationDto.getEvents() != null) {
            setCompilationEvents(compilation, compilationDto.getEvents());
        }

        compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilationByAdmin(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d was not found", compId)));
        compilationRepository.delete(compilation);
    }

    @Override
    public CompilationDto updateCompilationByAdmin(Long compId, UpdateCompilationRequest compilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d was not found", compId)));
        if (compilationRequest.getTitle() != null) {
            String title = compilationRequest.getTitle();
            if (title.isEmpty() || title.length() > 50) {
                throw new ValidationException("CompilationRequest title=%d must be min=1, max=50 characters");
            }
            compilation.setTitle(title);
        }
        if (compilationRequest.getPinned() != null) {
            compilation.setPinned(compilationRequest.getPinned());
        }
        if (compilationRequest.getEvents() != null) {
            setCompilationEvents(compilation, compilationRequest.getEvents());
        }

        compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilation);
    }

    private void setCompilationEvents(Compilation compilation, List<Long> eventIds) {
        List<Event> events = eventRepository.findByIdIn(eventIds);
        compilation.setEvents(events);
    }

}
