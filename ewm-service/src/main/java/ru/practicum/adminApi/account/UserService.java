package ru.practicum.adminApi.account;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(List<Long> userIds, int from, int size);

    UserDto create(NewUserRequest userRequest);

    void delete(Long userId);

}
