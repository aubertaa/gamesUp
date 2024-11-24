package com.gamesUP.gamesUP.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY)
public class RelationConstraintException extends RuntimeException {
    public RelationConstraintException(String message) {
        super(message);
    }
}