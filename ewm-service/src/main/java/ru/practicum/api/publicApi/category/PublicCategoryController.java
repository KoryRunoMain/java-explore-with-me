package ru.practicum.api.publicApi.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.responseDto.CategoryDto;
import ru.practicum.common.exception.NotFoundException;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoryController {
    private final PublicCategoryService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@Validated @Positive @RequestParam(defaultValue = "0") int from,
                                           @Validated @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("PUBLIC-GET-request, getCategories: from={}, size={}", from, size);
        List<CategoryDto> categories = service.getAllCategories(from, size);
        return Optional.ofNullable(categories).orElse(Collections.emptyList());
    }

    @GetMapping(path = "{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@Validated @Positive @PathVariable Long catId) {
        log.info("PUBLIC-GET-request, getCategory: catId={}", catId);
        CategoryDto category = service.getCategoryById(catId);
        return Optional.ofNullable(category).orElseThrow(() -> new NotFoundException("NotFound.."));
    }

}
