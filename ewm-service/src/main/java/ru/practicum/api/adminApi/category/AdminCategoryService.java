package ru.practicum.api.adminApi.category;

import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.api.requestDto.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto createCategoryByAdmin(NewCategoryDto categoryDto);

    void deleteCategoryByAdmin(Long catId);

    CategoryDto updateCategoryByAdmin(Long catId, NewCategoryDto categoryDto);

}
