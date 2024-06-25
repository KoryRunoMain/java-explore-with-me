package ru.practicum.api.adminApi.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.api.requestDto.NewCategoryDto;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Validated @RequestBody NewCategoryDto categoryDto) {
        log.info("Post-request: createCategory, name={}", categoryDto);
        return service.createCategoryByAdmin(categoryDto);
    }

    @DeleteMapping(path = "/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@Validated @Positive @PathVariable Long catId) {
        log.info("Delete-request: deleteCategory, catId={}", catId);
        service.deleteCategoryByAdmin(catId);
    }

    @PatchMapping(path = "/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@Validated @RequestBody NewCategoryDto categoryDto,
                                      @Validated @Positive @PathVariable Long catId) {
        log.info("Patch-request: updateCategory, catId={}, categoryDto={}", catId, categoryDto);
        return service.updateCategoryByAdmin(catId, categoryDto);
    }

}
