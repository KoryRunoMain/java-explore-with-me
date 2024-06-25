package ru.practicum.api.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

    private String email;
    private Long id;
    private String name;

}
