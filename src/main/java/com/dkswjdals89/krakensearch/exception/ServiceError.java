package com.dkswjdals89.krakensearch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ServiceError {
    REQUEST_VALIDATE_ERROR(HttpStatus.BAD_REQUEST.value(), "Request Data Validate Fail"),
    DUPLICATED_ERROR(HttpStatus.BAD_REQUEST.value(), "Duplicated Error"),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND.value(), "Not Found Account Info"),
    PASSWORD_ERROR(HttpStatus.NOT_EXTENDED.value(), "Password Does Not Match"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Service Error");

    private final Integer statusCode;
    private final String message;
}
