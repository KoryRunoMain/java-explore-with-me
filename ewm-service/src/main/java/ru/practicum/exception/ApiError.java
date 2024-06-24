package ru.practicum.exception;

import java.util.List;

public class ApiError {

    private List<String> errors;
    private String message;
    private String reason;
    private ApiStatus status;
    private String timestamp;

}
