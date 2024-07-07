package ru.practicum.common.exception;

public class ValidationRequestException extends RuntimeException {
    public ValidationRequestException(String message) {
        super(message);
    }
}
