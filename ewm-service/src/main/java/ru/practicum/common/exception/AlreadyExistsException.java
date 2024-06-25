package ru.practicum.common.exception;

import javax.persistence.EntityNotFoundException;

public class AlreadyExistsException extends EntityNotFoundException {

    public AlreadyExistsException(String message) {
        super(message);
    }

}
