package ru.practicum.common.exception;

public class InterruptedTreadException extends RuntimeException {
    public InterruptedTreadException(String message) {
        super(message);
    }
}
