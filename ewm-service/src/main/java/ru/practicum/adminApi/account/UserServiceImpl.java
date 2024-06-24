package ru.practicum.adminApi.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private UserMapper mapper;

    @Override
    public List<UserDto> getUsers(List<Long> userIds, int from, int size) {
        int pageNumber = from / size;
        Pageable page = PageRequest.of(pageNumber, size);
        if (userIds != null) {
            return getUsersWithListIds(userIds, page);
        }
        return getUsersWithoutListIds(page);
    }

    @Override
    public UserDto create(NewUserRequest userRequest) {
        return mapper.toUserDto(repository.save(mapper.toUser(userRequest)));
    }

    @Override
    public void delete(Long userId) {
        repository.deleteById(userId);
    }

    private List<UserDto> getUsersWithListIds(List<Long> ids, Pageable page) {
        return repository.findAllByIdIn(ids, page).stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

    private List<UserDto> getUsersWithoutListIds(Pageable page) {
        return repository.findAll(page).stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

}
