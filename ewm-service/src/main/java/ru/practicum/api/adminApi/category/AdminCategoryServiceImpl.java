package ru.practicum.api.adminApi.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.common.exception.ForbiddenException;
import ru.practicum.persistence.repository.CategoryRepository;
import ru.practicum.persistence.model.Category;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.api.requestDto.NewCategoryDto;
import ru.practicum.common.mapper.CategoryMapper;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.persistence.repository.EventRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategoryByAdmin(NewCategoryDto newCategoryDto) {
        Category newCategory = categoryMapper.toCategory(newCategoryDto);
        categoryRepository.save(newCategory);
        return categoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public void deleteCategoryByAdmin(Long catId) {
        categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
        boolean categoryInUse = eventRepository.existsByCategoryId(catId);
        if (categoryInUse) {
            throw new ForbiddenException(String.format("Category with id=%d is not Empty", catId));
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategoryByAdmin(Long catId, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
        Category newCat = categoryMapper.toCategory(newCategoryDto);
        newCat.setId(category.getId());

        categoryRepository.save(newCat);
        return categoryMapper.toCategoryDto(newCat);
    }

}
