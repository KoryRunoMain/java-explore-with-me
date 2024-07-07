package ru.practicum.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.common.enums.ApiStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiError {

    private ApiStatus status;
    private String reason;
    private String message;
    private String timestamp;

}
