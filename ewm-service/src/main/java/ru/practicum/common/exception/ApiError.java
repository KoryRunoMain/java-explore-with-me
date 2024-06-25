package ru.practicum.common.exception;

import ru.practicum.common.enums.ApiStatus;

import java.util.List;

public class ApiError {

    private List<String> errors;
    private String message;
    private String reason;
    private ApiStatus status;
    private String timestamp;

}
