package ru.practicum.common.exception;

import javax.persistence.EntityNotFoundException;

public class ValidationException extends EntityNotFoundException {
    public ValidationException(String message) {
        super(message);
    }
}
