package ru.practicum.api.publicApi.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.CompilationDto;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.mapper.CompilationMapper;
import ru.practicum.persistence.model.Compilation;
import ru.practicum.persistence.repository.CompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from/size, size);
        List<Compilation> compilations = (pinned != null)
                ? compilationRepository.findByPinned(pinned, pageable)
                : compilationRepository.findAll(pageable).getContent();
        return compilations.stream()
                .map(compilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return compilationMapper.toCompilationDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("PUBLIC-MESSAGE-response: category NotFound")));
    }

}
