package com.phuongnh.personal.library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthorIdsInvalidOrNotFoundException extends RuntimeException {
    public AuthorIdsInvalidOrNotFoundException() {
        super("Invalid or not found author IDs");
    }
}
