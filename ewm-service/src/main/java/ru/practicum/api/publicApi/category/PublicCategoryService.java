package ru.practicum.api.publicApi.category;

import ru.practicum.api.responseDto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(Long catId);

}
