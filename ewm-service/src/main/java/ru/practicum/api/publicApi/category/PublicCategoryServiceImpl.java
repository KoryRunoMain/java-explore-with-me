package ru.practicum.api.publicApi.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.common.mapper.CategoryMapper;
import ru.practicum.persistence.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll(PageRequest.of(from / size, size))
                .stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
        return categoryDtoList.isEmpty() ? Collections.emptyList() : categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return categoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found,", catId))));
    }

}
