package com.navvish.germanphonesparser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ValidPhoneNumbersNotFound extends RuntimeException {

    public ValidPhoneNumbersNotFound(String message) {
        super(message);
    }
}
