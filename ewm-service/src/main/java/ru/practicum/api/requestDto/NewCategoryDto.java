package ru.practicum.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class NewCategoryDto {

    @Size(min = 1, max = 50)
    private String name;

}
