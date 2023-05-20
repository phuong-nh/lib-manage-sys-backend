package com.phuongnh.personal.library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IsbnAlreadyExistsException extends RuntimeException {
    public IsbnAlreadyExistsException() {
        super("ISBN already exists");
    }
}
