package ru.practicum.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ValidationRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseError validationExceptionHandle(ValidationRequestException e) {
        return new ApiResponseError(
                "BAD_REQUEST",
                "Incorrectly made request.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseError handleMissingParams(MissingServletRequestParameterException e) {
        return new ApiResponseError(
                "BAD_REQUEST",
                "Missing required parameter.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseError throwableExceptionHandle(Throwable e) {
        return new ApiResponseError(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

}
