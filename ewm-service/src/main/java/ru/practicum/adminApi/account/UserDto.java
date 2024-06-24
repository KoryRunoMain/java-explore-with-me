package ru.practicum.adminApi.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

    private Long id;
    private String email;
    private String name;

}
