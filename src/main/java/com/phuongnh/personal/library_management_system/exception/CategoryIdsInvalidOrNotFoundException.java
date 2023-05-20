package com.phuongnh.personal.library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryIdsInvalidOrNotFoundException extends RuntimeException {
    public CategoryIdsInvalidOrNotFoundException() {
        super("Invalid or not found category IDs");
    }
}
