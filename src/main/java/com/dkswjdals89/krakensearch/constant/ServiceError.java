package com.dkswjdals89.krakensearch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ServiceError {
    REQUEST_VALIDATE_ERROR(HttpStatus.BAD_REQUEST.value(), "Request Data Validate Fail"),
    DUPLICATED_ERROR(HttpStatus.BAD_REQUEST.value(), "Duplicated Error");

    private Integer statusCode;
    private String message;
}
