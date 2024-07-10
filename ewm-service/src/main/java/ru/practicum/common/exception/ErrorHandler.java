package ru.practicum.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.common.enums.ApiStatus;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationExceptionHandle(ValidationException e) {
        return new ApiError(
                ApiStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ApiError(
                ApiStatus.BAD_REQUEST,
                "Validation failed.",
                "Incorrectly made request.",
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBindException(BindException e) {
        return new ApiError(
                ApiStatus.BAD_REQUEST,
                "Validation failed.",
                "Incorrectly made request.",
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingParams(MissingServletRequestParameterException e) {
        String name = e.getParameterName();
        return new ApiError(
                ApiStatus.BAD_REQUEST,
                "Missing request parameter",
                String.format("Required request parameter '%s' is not present", name),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundExceptionHandle(NotFoundException e) {
        return new ApiError(
                ApiStatus.NOT_FOUND,
                "The required object was not found.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(InvalidStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError invalidStateException(InvalidStateException e) {
        return new ApiError(
                ApiStatus.NOT_FOUND,
                "Invalid state.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError alreadyExistExceptionHandler(AlreadyExistsException e) {
        return new ApiError(
                ApiStatus.CONFLICT,
                "Integrity constraint has been violated.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ApiError(
                ApiStatus.CONFLICT,
                "Validation failed.",
                "Incorrectly made request.",
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError forbiddenExceptionHandler(ForbiddenException e) {
        return new ApiError(
                ApiStatus.FORBIDDEN,
                "For the requested operation the conditions are not met.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(InterruptedTreadException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError interruptedExceptionHandler(InterruptedTreadException e) {
        return new ApiError(
                ApiStatus.CONFLICT,
                "Interrupted because of problems with tread",
                e.getMessage(),
                LocalDateTime.MIN.format(formatter)
        );
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError throwableExceptionHandle(Throwable e) {
        return new ApiError(
                ApiStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

}
