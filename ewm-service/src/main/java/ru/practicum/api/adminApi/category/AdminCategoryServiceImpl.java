package ru.practicum.api.adminApi.category;

import lombok.RequiredArgsConstructor;
import ru.practicum.common.exception.ForbiddenException;
import ru.practicum.persistence.model.Event;
import ru.practicum.persistence.repository.CategoryRepository;
import ru.practicum.persistence.model.Category;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.api.requestDto.NewCategoryDto;
import ru.practicum.common.mapper.CategoryMapper;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.persistence.repository.EventRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategoryByAdmin(NewCategoryDto categoryDto) {
        return categoryMapper.toCategoryDto(
                categoryRepository.save(categoryMapper.toCategory(categoryDto)));
    }

    @Override
    public void deleteCategoryByAdmin(Long catId) {
        categoryRepository.findById(catId).orElseThrow(
                () -> new NotFoundException("ADMIN-ERROR-response: category NotFound"));
        Event event = eventRepository.findFirstByCategoryId(catId).orElseThrow(
                () -> new NotFoundException("ADMIN-ERROR-response: event NotFound"));
        if (event == null) {
            eventRepository.deleteById(catId);
        } else {
            throw  new ForbiddenException("ADMIN-ERROR-response: category is NotEmpty");
        }
    }

    @Override
    public CategoryDto updateCategoryByAdmin(Long catId, NewCategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId).orElseThrow(
                () -> new NotFoundException(catId.toString()));
        Category newCat = categoryMapper.toCategory(categoryDto);
        newCat.setId(category.getId());
        return categoryMapper.toCategoryDto(categoryRepository.save(newCat));
    }

}
