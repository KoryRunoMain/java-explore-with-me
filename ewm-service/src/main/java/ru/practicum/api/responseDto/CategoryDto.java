package ru.practicum.api.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class CategoryDto {

    private Long id;
    private String name;

}
