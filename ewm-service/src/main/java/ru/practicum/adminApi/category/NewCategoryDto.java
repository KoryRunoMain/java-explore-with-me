package ru.practicum.adminApi.category;

import javax.validation.constraints.Size;

public class NewCategoryDto {

    @Size(min = 1, max = 50)
    private String name;

}
