package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.persistence.model.Category;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.api.requestDto.NewCategoryDto;

@Component
public class CategoryMapper {

    public Category toCategory(NewCategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
