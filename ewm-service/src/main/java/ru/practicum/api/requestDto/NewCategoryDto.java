package ru.practicum.api.requestDto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
public class NewCategoryDto {

    @Size(min = 1, max = 50)
    private String name;

}
