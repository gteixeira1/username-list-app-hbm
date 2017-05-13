package com.intertec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid input data")
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(String message) {
        super(message);
    }

    public InvalidInputDataException(String message, Object... args) {
        super(MessageFormat.format(message,args));
    }

    public InvalidInputDataException(Exception e) {
        this(e.getMessage());
        String exceptionMessage = MessageFormat.format("Request failed due to: {0}", e.getMessage());
    }
}
