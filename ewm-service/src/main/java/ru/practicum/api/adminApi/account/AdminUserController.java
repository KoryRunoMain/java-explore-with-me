package ru.practicum.api.adminApi.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.requestDto.NewUserRequest;
import ru.practicum.api.responseDto.UserDto;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final AdminUserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        log.info("Get-request: userIdList={}, from={}, size={}", ids, from, size);
        return service.getUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Validated @RequestBody NewUserRequest userRequest) {
        log.info("Post-request: userRequest={}", userRequest);
        return service.create(userRequest);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@Validated @Positive @PathVariable Long id) {
        log.info("Delete-request: userId={}", id);
        service.delete(id);
    }

}
