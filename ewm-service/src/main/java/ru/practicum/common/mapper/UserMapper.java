package ru.practicum.common.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.api.requestDto.NewUserRequest;
import ru.practicum.persistence.model.User;
import ru.practicum.api.responseDto.UserDto;
import ru.practicum.api.responseDto.UserShortDto;

@Component
public class UserMapper {

    public User toUser(NewUserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .name(user.getName())
                .id(user.getId())
                .build();
    }

}
