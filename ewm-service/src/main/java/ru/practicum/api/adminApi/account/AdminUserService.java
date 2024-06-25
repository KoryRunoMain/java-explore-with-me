package ru.practicum.api.adminApi.account;

import ru.practicum.api.requestDto.NewUserRequest;
import ru.practicum.api.responseDto.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getUsers(List<Long> userIds, int from, int size);

    UserDto create(NewUserRequest userRequest);

    void delete(Long userId);

}
