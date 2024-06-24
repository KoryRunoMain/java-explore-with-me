package ru.practicum.exception;

import javax.persistence.EntityNotFoundException;

public class AlreadyExistsException extends EntityNotFoundException {

    public AlreadyExistsException(String message) {
        super(message);
    }

}
