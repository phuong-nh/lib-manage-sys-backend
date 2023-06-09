package com.phuongnh.personal.library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughAvailableCopiesException extends RuntimeException {
    public NotEnoughAvailableCopiesException() {
        super("Not enough available copies");
    }
}
